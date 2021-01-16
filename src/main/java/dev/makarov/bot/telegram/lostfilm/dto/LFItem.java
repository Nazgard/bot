package dev.makarov.bot.telegram.lostfilm.dto;

public class LFItem {

    private String title;
    private String description;
    private String pubDate;
    private String link;


    public String getTitle() {
        return title;
    }

    public LFItem setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public LFItem setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getPubDate() {
        return pubDate;
    }

    public LFItem setPubDate(String pubDate) {
        this.pubDate = pubDate;
        return this;
    }

    public String getLink() {
        return link;
    }

    public LFItem setLink(String link) {
        this.link = link;
        return this;
    }
}
