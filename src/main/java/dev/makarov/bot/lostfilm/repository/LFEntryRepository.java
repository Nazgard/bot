package dev.makarov.bot.lostfilm.repository;

import dev.makarov.bot.lostfilm.entity.LFEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface LFEntryRepository extends MongoRepository<LFEntry, ObjectId> {

    Optional<LFEntry> findByName(String name);

}
