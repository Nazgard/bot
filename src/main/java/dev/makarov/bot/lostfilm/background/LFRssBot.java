package dev.makarov.bot.lostfilm.background;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import dev.makarov.bot.lostfilm.configuration.LFRestTemplate;
import dev.makarov.bot.lostfilm.dto.LFRss;
import dev.makarov.bot.lostfilm.persistance.repository.LFEntryRepository;
import dev.makarov.bot.lostfilm.queue.LFItemQueue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class LFRssBot {

    private static final String URL = "https://www.lostfilmtv.site/rss.xml";

    private final XmlMapper xmlMapper = new XmlMapper();

    private final LFRestTemplate restTemplate;
    private final LFItemQueue queue;
    //TODO убрать зависимость от репозитория
    private final LFEntryRepository repository;

    @Scheduled(fixedDelay = 60_000)
    public void refreshState() {
        try {
            ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.GET, null, String.class);
            LFRss rss = xmlMapper.readValue(response.getBody(), LFRss.class);
            queue.addAll(rss.getChannel().getItems().stream()
                    .filter(i -> !repository.existsByOriginUrl(i.getLink()))
                    .collect(Collectors.toList()));
        } catch (IOException e) {
            log.error(e.getLocalizedMessage(), e);
        }
    }

}
