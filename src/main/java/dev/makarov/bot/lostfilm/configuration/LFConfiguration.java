package dev.makarov.bot.lostfilm.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Data
@ConstructorBinding
@ConfigurationProperties(prefix = "lostfilm")
public class LFConfiguration {

    private String cookie;

}
