package dev.makarov.bot.telegram.lostfilm.dto;

import java.util.ArrayList;
import java.util.List;

public class LFChannel {

    private String title;
    private String description;
    private String link;
    private String lastBuildDate;
    private String language;
    private List<LFItem> items = new ArrayList<>();


    public String getTitle() {
        return title;
    }

    public LFChannel setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public LFChannel setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getLink() {
        return link;
    }

    public LFChannel setLink(String link) {
        this.link = link;
        return this;
    }

    public String getLastBuildDate() {
        return lastBuildDate;
    }

    public LFChannel setLastBuildDate(String lastBuildDate) {
        this.lastBuildDate = lastBuildDate;
        return this;
    }

    public String getLanguage() {
        return language;
    }

    public LFChannel setLanguage(String language) {
        this.language = language;
        return this;
    }

    public List<LFItem> getItems() {
        return items;
    }

    public LFChannel setItems(List<LFItem> items) {
        this.items = items;
        return this;
    }
}
