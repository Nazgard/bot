package dev.makarov.bot.lostfilm.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@Builder
public class LFParsedItem {

    private String name;
    private List<LFParsedItemTorrent> torrents;
    private Instant created;
    private Instant pubDate;
    private String originUrl;

}
