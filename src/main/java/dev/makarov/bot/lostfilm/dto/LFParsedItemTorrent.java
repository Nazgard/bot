package dev.makarov.bot.lostfilm.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Builder
public class LFParsedItemTorrent {

    private String name;
    private String quality;
    private String torrentUrl;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private byte[] torrentFile;

}
