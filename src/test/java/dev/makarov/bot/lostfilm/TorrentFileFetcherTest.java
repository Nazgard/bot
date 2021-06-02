package dev.makarov.bot.lostfilm;

import dev.makarov.bot.lostfilm.dto.LFParsedItem;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TorrentFileFetcherTest {

    @Test
    void testFetch() {
        LFItemQueue inputQueue = new LFItemQueue();
        LFParsedItemQueue outputQueue = new LFParsedItemQueue();
        RssBot bot = new RssBot(inputQueue);
        bot.refreshState();
        TorrentFileFetcher fetcher = new TorrentFileFetcher(inputQueue, outputQueue, new LFConfiguration());
        fetcher.postConstruct();
        fetcher.fetch();
        LFParsedItem item = outputQueue.poll();
        assertNotNull(item);
        assertNotNull(item.getName());
        assertEquals(3, item.getTorrents().size());
    }

}