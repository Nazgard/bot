package dev.makarov.bot.telegram;

import dev.makarov.bot.telegram.datecalc.DateCalculateService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.telegram.telegrambots.meta.api.objects.EntityType.BOTCOMMAND;

@Service
public class Router {

    private static final Logger LOGGER = LogManager.getLogger(Router.class);

    private final DateCalculateService calcService;

    public Router(DateCalculateService calcService) {
        this.calcService = calcService;
    }

    public List<SendMessage> route(Update update) {
        List<SendMessage> apiMethods = new ArrayList<>();
        Optional.ofNullable(update)
                .filter(Update::hasMessage)
                .map(Update::getMessage)
                .map(Message::getEntities)
                .stream()
                .flatMap(Collection::stream)
                .filter(messageEntity -> messageEntity.getType().equals(BOTCOMMAND))
                .forEach(messageEntity -> {
                    try {
                        Command command = Command.parseCommand(messageEntity.getText());
                        switch (command) {
                            case DD: apiMethods.addAll(calcService.calc(update));
                        }
                    } catch (NotCommandException e) {
                        LOGGER.debug("Command not resolved {}", messageEntity.getText());
                    }
                });
        return apiMethods;
    }

}
