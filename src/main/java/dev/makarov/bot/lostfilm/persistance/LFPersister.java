package dev.makarov.bot.lostfilm.persistance;

import dev.makarov.bot.lostfilm.dto.LFParsedItem;

public interface LFPersister {

    void persist(LFParsedItem item);

}
