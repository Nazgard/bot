package dev.makarov.bot.telegram.lostfilm;

import dev.makarov.bot.telegram.lostfilm.dto.LFItem;
import dev.makarov.bot.utils.FixedArrayList;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;

import static java.net.http.HttpResponse.BodyHandlers.ofString;

@Service
public class RssBot {

    private static final String URL = "https://lostfilm.tv/rss.xml";
    private final List<LFItem> state = new FixedArrayList<>(15);
    private final HttpClient httpClient = HttpClient.newHttpClient();

    @Scheduled(fixedDelay = 60_000)
    public void refreshState() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL))
                .timeout(Duration.ofSeconds(10))
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, ofString(StandardCharsets.UTF_8));
            response.body();
        } catch (IOException | InterruptedException e) {

        }
    }

    public List<LFItem> getState() {
        return state;
    }
}
