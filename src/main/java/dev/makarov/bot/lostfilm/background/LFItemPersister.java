package dev.makarov.bot.lostfilm.background;

import dev.makarov.bot.lostfilm.persistance.LFPersister;
import dev.makarov.bot.lostfilm.dto.LFParsedItem;
import dev.makarov.bot.lostfilm.queue.LFParsedItemQueue;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class LFItemPersister {

    private final LFParsedItemQueue queue;
    private final LFPersister persister;

    @SneakyThrows
    @Scheduled(fixedDelay = 100)
    public void persist() {
        LFParsedItem item = queue.poll(5, TimeUnit.SECONDS);
        if (item == null) {
            return;
        }
        persister.persist(item);
    }

}
