package dev.makarov.bot.lostfilm.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
public class LFRss {

    @JacksonXmlProperty(isAttribute = true)
    private String version;
    private LFChannel channel;

}
