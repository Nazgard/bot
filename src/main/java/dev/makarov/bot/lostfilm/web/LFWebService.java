package dev.makarov.bot.lostfilm.web;

import org.bson.types.ObjectId;
import org.springframework.core.io.Resource;

import java.io.FileNotFoundException;

public interface LFWebService {

    LFRss getRss(String quantity);

    Resource getTorrent(ObjectId id) throws FileNotFoundException;

}
