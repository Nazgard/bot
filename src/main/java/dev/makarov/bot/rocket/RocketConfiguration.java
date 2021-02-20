package dev.makarov.bot.rocket;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Data
@ConstructorBinding
@ConfigurationProperties(prefix = "rocket")
public class RocketConfiguration {

    private final Boolean enabled;
    private final String url;
    private final String authToken;
    private final String userId;

}
