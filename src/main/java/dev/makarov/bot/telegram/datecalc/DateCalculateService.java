package dev.makarov.bot.telegram.datecalc;

import dev.makarov.bot.telegram.Command;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Service
public class DateCalculateService {

    public List<SendMessage> calc(Update update) {
        SendMessage message = new SendMessage();
        message.setChatId(update.getMessage().getChatId().toString());
        message.setReplyToMessageId(update.getMessage().getMessageId());
        String msg = update.getMessage().getText().replaceFirst(Command.DD.getCode(), "").trim();
        DateCalculator calculator = new DateCalculator(msg);
        calculator.getOutput().ifPresent(message::setText);
        return List.of(message);
    }

}
