package dev.makarov.bot.telegram;

import org.telegram.telegrambots.bots.DefaultBotOptions;

public class PersonalBotOptions extends DefaultBotOptions {

    public PersonalBotOptions() {
        super();
        setGetUpdatesTimeout(1_000);
    }
}
