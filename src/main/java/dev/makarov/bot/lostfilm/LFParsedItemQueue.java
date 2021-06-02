package dev.makarov.bot.lostfilm;

import dev.makarov.bot.lostfilm.dto.LFParsedItem;
import org.springframework.stereotype.Service;

import java.util.concurrent.ArrayBlockingQueue;

@Service
public class LFParsedItemQueue extends ArrayBlockingQueue<LFParsedItem> {

    public LFParsedItemQueue() {
        super(1_000);
    }
}
