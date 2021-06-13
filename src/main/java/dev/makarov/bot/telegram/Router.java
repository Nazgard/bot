package dev.makarov.bot.telegram;

import dev.makarov.bot.telegram.datecalc.DateCalculateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.telegram.telegrambots.meta.api.objects.EntityType.BOTCOMMAND;

@Slf4j
@Service
@RequiredArgsConstructor
public class Router {

    private final DateCalculateService calcService;

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
                            case DD:
                                apiMethods.addAll(calcService.calc(update));
                                break;
//                            case RSS:
//                                apiMethods.addAll(rssBot.getState());
//                                break;
                        }
                    } catch (NotCommandException e) {
                        log.debug("Command not resolved {}", messageEntity.getText());
                    }
                });
        return apiMethods;
    }

}
