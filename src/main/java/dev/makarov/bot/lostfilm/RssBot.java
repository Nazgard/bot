package dev.makarov.bot.lostfilm;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import dev.makarov.bot.lostfilm.dto.LFRss;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

import static java.net.http.HttpResponse.BodyHandlers.ofString;

@Slf4j
@Service
@RequiredArgsConstructor
public class RssBot {

    private static final String URL = "https://www.lostfilmtv.site/rss.xml";

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final XmlMapper xmlMapper = new XmlMapper();

    private final LFItemQueue queue;

    @Scheduled(fixedDelay = 60_000)
    public void refreshState() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL))
                .timeout(Duration.ofSeconds(10))
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, ofString(StandardCharsets.UTF_8));
            LFRss rss = xmlMapper.readValue(response.body(), LFRss.class);
            queue.addAll(rss.getChannel().getItems());
        } catch (IOException | InterruptedException e) {
            log.error(e.getLocalizedMessage(), e);
        }
    }

}
