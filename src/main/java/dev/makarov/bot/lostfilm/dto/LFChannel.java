package dev.makarov.bot.lostfilm.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
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
    @JacksonXmlProperty(localName = "item")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<LFItem> items = new ArrayList<>();

}
