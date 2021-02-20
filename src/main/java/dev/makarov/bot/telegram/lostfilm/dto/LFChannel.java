package dev.makarov.bot.telegram.lostfilm.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class LFChannel {

    private String title;
    private String description;
    private String link;
    private String lastBuildDate;
    private String language;
    private List<LFItem> items = new ArrayList<>();

}
