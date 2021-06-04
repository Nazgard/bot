package dev.makarov.bot.lostfilm.background;

import dev.makarov.bot.lostfilm.configuration.LFRestTemplate;
import dev.makarov.bot.lostfilm.dto.LFItem;
import dev.makarov.bot.lostfilm.dto.LFParsedItem;
import dev.makarov.bot.lostfilm.dto.LFParsedItemTorrent;
import dev.makarov.bot.lostfilm.queue.LFItemQueue;
import dev.makarov.bot.lostfilm.queue.LFParsedItemQueue;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
public class LFTorrentFileFetcher {

    private final HttpHeaders headers = new HttpHeaders();
    private final LFRestTemplate restTemplate;
    private final LFItemQueue inputQueue;
    private final LFParsedItemQueue outputQueue;

    @SneakyThrows
    @Scheduled(fixedDelay = 100)
    public void fetch() {
        LFItem item = inputQueue.poll(5, TimeUnit.SECONDS);
        if (item == null) {
            return;
        }
        parseEpisodePage(item)
                .flatMap(this::parseSearch)
                .map(this::parseSearchPage)
                .ifPresent(outputQueue::offer);
    }

    private Optional<FetchLfItem> parseEpisodePage(LFItem item) {
        String url = item.getLink().replaceFirst("/mr/", "/");
        Optional<Long> playEpisodeId = getMatcher("PlayEpisode\\('(\\d+)'\\)", getBody(url))
                .map(m -> Long.valueOf(m.group(1)));
        if (playEpisodeId.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(FetchLfItem.builder()
                .lfItem(item)
                .playEpisodeId(playEpisodeId.get())
                .build());
    }

    private Optional<FetchLfItem> parseSearch(FetchLfItem fetchLfItem) {
        String url = "https://www.lostfilmtv.site/v_search.php?a=" + fetchLfItem.getPlayEpisodeId();
        Optional<String> tracktorUrl = getMatcher("url=(\\s*\\S+)\"", getBody(url)).map(m -> m.group(1));
        if (tracktorUrl.isEmpty()) {
            return Optional.empty();
        }
        fetchLfItem.setTracktorUrl(tracktorUrl.get());
        return Optional.of(fetchLfItem);
    }

    private LFParsedItem parseSearchPage(FetchLfItem fetchLfItem) {
        String body = getBody(fetchLfItem.getTracktorUrl());
        Document document = Jsoup.parse(body);
        String itemName = document.getElementsByClass("inner-box--text").iterator().next().text();
        Elements elements = document.getElementsByClass("inner-box--item");
        List<LFParsedItemTorrent> torrents = new ArrayList<>(3);
        LFParsedItem item = LFParsedItem.builder()
                .name(itemName)
                .torrents(torrents)
                .created(Instant.now())
                .pubDate(fetchLfItem.getLfItem().getPubDate().toInstant())
                .originUrl(fetchLfItem.getLfItem().getLink())
                .build();
        for (Element element : elements) {
            String torrentName = element.getElementsByClass("inner-box--link main").iterator().next().text();
            String torrentQuality = element.getElementsByClass("inner-box--label").iterator().next().text();
            String torrentUrl = element.getElementsByClass("inner-box--link sub").iterator().next().text();
            byte[] torrentFile = getBodyBytes(torrentUrl);
            torrents.add(LFParsedItemTorrent.builder()
                    .name(torrentName)
                    .quality(torrentQuality)
                    .torrentUrl(torrentUrl)
                    .torrentFile(torrentFile)
                    .build());
        }
        return item;
    }

    private String getBody(String url) {
        return new String(getBodyBytes(url));
    }

    private byte[] getBodyBytes(String url) {
        ResponseEntity<byte[]> exchange = restTemplate
                .exchange(url, HttpMethod.GET, new HttpEntity<String>(headers), byte[].class);
        if (exchange.getStatusCode().isError()) {
            throw new LFParseHttpError();
        }
        byte[] body = exchange.getBody();
        if (body == null) {
            throw new LFParseEmptyBody();
        }
        return body;
    }

    private Optional<Matcher> getMatcher(String pattern, String body) {
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(body);
        if (!m.find()) {
            return Optional.empty();
        }
        return Optional.of(m);
    }

    private static class LFParseHttpError extends RuntimeException {

    }

    private static class LFParseEmptyBody extends RuntimeException {

    }

    @Data
    @Builder
    private static class FetchLfItem {
        private LFItem lfItem;
        private Long playEpisodeId;
        private String tracktorUrl;
    }

}
