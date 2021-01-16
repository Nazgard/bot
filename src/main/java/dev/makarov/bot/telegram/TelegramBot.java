package dev.makarov.bot.telegram;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class TelegramBot extends TelegramLongPollingBot {

    private static final Logger LOGGER = LogManager.getLogger(TelegramBot.class);

    private final TelegramConfiguration configuration;
    private final Router router;

    public TelegramBot(TelegramConfiguration configuration, Router router) {
        this.configuration = configuration;
        this.router = router;
    }

    @PostConstruct
    public void postConstruct() {
        LOGGER.info("TelegramBot loaded");
    }

    @Override
    public void onUpdateReceived(Update update) {
        LOGGER.debug("update received");
        LOGGER.debug("route");
        List<SendMessage> apiMethods = router.route(update);
        LOGGER.debug("routed");
        for (SendMessage apiMethod : apiMethods) {
            try {
                execute(apiMethod);
            } catch (TelegramApiException e) {
                LOGGER.error(e.getMessage(), e);
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
        LOGGER.info("Telegram Bot registered");
    }

    @Override
    public void onClosing() {
        LOGGER.info("Telegram Bot closing");
    }
}
