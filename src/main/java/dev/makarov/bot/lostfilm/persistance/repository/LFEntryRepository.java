package dev.makarov.bot.lostfilm.persistance.repository;

import dev.makarov.bot.lostfilm.persistance.entity.LFEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LFEntryRepository extends MongoRepository<LFEntry, ObjectId> {

    boolean existsByOriginUrl(String originUrl);

    boolean existsByName(String name);

}
