package dev.makarov.bot.telegram;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {

    private final TelegramConfiguration configuration;
    private final Router router;

    @PostConstruct
    public void postConstruct() {
        log.info("TelegramBot loaded");
    }

    @Override
    public void onUpdateReceived(Update update) {
        log.debug("update received");
        log.debug("route");
        List<SendMessage> apiMethods = router.route(update);
        log.debug("routed");
        for (SendMessage apiMethod : apiMethods) {
            try {
                execute(apiMethod);
            } catch (TelegramApiException e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    @Override
    public void clearWebhook() throws TelegramApiRequestException {

    }

    @Override
    public String getBotUsername() {
        return configuration.getBotUsername();
    }

    @Override
    public String getBotToken() {
        return configuration.getBotToken();
    }

    @Override
    public void onRegister() {
        log.info("Telegram Bot registered");
    }

    @Override
    public void onClosing() {
        log.info("Telegram Bot closing");
    }
}
