package dev.makarov.bot.lostfilm.background;

import dev.makarov.bot.lostfilm.dto.LFItem;
import dev.makarov.bot.lostfilm.persistance.repository.LFEntryRepository;
import dev.makarov.bot.lostfilm.queue.LFItemQueue;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class LFMainPageBot {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private final LFItemQueue queue;
    //TODO убрать зависимость от репозитория
    private final LFEntryRepository repository;

    @SneakyThrows
    @Scheduled(fixedDelay = 60_000)
    public void fetch() {
        Document d = Jsoup.parse(new URL("https://www.lostfilmtv.site/new/"), 30_000);
        for (Element row : d.getElementsByClass("row")) {
            String link = "https://www.lostfilmtv.uno/mr" + row.getElementsByTag("a").get(0).attributes().get("href");
            if (repository.existsByOriginUrl(link)) {
                continue;
            }
            String serialName = row.getElementsByClass("name-ru").text();
            String seriesNameRu = row.getElementsByClass("alpha").get(0).text();
            String ruDateRaw = row.getElementsByClass("alpha").get(1).text().replace("Дата выхода Ru: ", "");
            queue.add(LFItem.builder()
                    .title(serialName + ". " + seriesNameRu)
                    .pubDate(Date.from(LocalDate.parse(ruDateRaw, FORMATTER).atStartOfDay().toInstant(ZoneOffset.UTC)))
                    .link(link)
                    .build());
        }
    }

}
