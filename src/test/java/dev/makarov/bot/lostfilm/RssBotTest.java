package dev.makarov.bot.lostfilm;

import dev.makarov.bot.lostfilm.dto.LFItem;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class RssBotTest {

    @Test
    void testRefreshState() {
        LFItemQueue queue = new LFItemQueue();
        RssBot rssBot = new RssBot(queue);
        rssBot.refreshState();
        List<LFItem> state = new ArrayList<>(queue);
        assertNotNull(state);
    }

}