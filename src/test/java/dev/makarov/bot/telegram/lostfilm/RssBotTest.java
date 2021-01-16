package dev.makarov.bot.telegram.lostfilm;

import dev.makarov.bot.telegram.lostfilm.dto.LFItem;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RssBotTest {

    @Test
    public void testRefreshState() {
        RssBot rssBot = new RssBot();
        rssBot.refreshState();
        List<LFItem> state = rssBot.getState();
        assertNotNull(state);
    }

}