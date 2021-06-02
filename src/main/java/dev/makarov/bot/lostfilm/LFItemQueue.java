package dev.makarov.bot.lostfilm;

import dev.makarov.bot.lostfilm.dto.LFItem;
import org.springframework.stereotype.Service;

import java.util.concurrent.ArrayBlockingQueue;

@Service
public class LFItemQueue extends ArrayBlockingQueue<LFItem> {

    public LFItemQueue() {
        super(1_000);
    }

}
