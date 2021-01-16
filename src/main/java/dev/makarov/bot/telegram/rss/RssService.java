package dev.makarov.bot.telegram.rss;

import com.rometools.rome.io.impl.RSS20Parser;
import dev.makarov.bot.telegram.Command;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Service
public class RssService {

    public List<SendMessage> addToCheck(Update update) {
        SendMessage message = new SendMessage();
        message.setChatId(update.getMessage().getChatId().toString());
        message.setReplyToMessageId(update.getMessage().getMessageId());
        update.getMessage().getText().replaceFirst(Command.DD.getCode(), "").trim();
        return List.of(message);
    }

    @Scheduled(fixedDelay = 5_000)
    public void refreshKinozal() {
        RSS20Parser parser = new RSS20Parser();
    }

}
