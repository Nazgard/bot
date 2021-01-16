package dev.makarov.bot;

import dev.makarov.bot.rocket.RocketConfiguration;
import dev.makarov.bot.spotify.SpotifyConfiguration;
import dev.makarov.bot.telegram.TelegramConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties({
        RocketConfiguration.class,
        SpotifyConfiguration.class,
        TelegramConfiguration.class
})
public class BotApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(BotApplication.class, args);
    }

}
