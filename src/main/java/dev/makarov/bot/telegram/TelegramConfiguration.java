package dev.makarov.bot.telegram;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "telegram")
@RequiredArgsConstructor
@Data
public class TelegramConfiguration {

    private final String botUsername;
    private final String botToken;

}
