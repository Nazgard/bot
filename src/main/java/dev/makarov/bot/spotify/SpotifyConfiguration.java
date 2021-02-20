package dev.makarov.bot.spotify;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Data
@ConstructorBinding
@ConfigurationProperties(prefix = "spotify")
public class SpotifyConfiguration {

    private final String accessToken;

}
