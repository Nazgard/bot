package dev.makarov.bot.lostfilm.web;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LFRssChannel {

    private String title;
    private String link;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="EEE, dd MMM yyyy HH:mm:ss Z", locale = "en")
    private Date lastBuildDate;

    @Builder.Default
    @JacksonXmlProperty(localName = "item")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<LFRssChannelItem> items = new ArrayList<>();

}
