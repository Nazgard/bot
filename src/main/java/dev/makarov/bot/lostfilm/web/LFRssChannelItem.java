package dev.makarov.bot.lostfilm.web;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LFRssChannelItem {

    private String title;
    private String link;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="EEE, dd MMM yyyy HH:mm:ss Z", locale = "en")
    private Date pubDate;
    private String originalUrl;
    private String uid;

}
