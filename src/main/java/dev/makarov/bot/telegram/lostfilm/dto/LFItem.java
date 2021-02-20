package dev.makarov.bot.telegram.lostfilm.dto;

import lombok.Data;

@Data
public class LFItem {

    private String title;
    private String description;
    private String pubDate;
    private String link;

}
