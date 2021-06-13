package dev.makarov.bot.lostfilm.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LFRssChannelItem {

    private String title;
    private String link;
    private Instant pubDate;
    private String originalUrl;

}
