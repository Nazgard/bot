package dev.makarov.bot.lostfilm.background;

import com.mongodb.client.gridfs.model.GridFSFile;
import dev.makarov.bot.lostfilm.persistance.entity.LFEntry;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LFTorrentCleaner {

    private final GridFsTemplate gridFsTemplate;
    private final MongoTemplate mongoTemplate;

    @SneakyThrows
    @Scheduled(initialDelay = 10_000, fixedDelay = (60_000 * 60))
    public void clear() {
        log.info("Clear unused torrent files");
        for (GridFSFile file : gridFsTemplate.find(new BasicQuery(new Document()))) {
            Document lfEntryTorrentId = new Document();
            lfEntryTorrentId.put("torrents.gridFsObjectId", file.getObjectId());
            if (!mongoTemplate.exists(new BasicQuery(lfEntryTorrentId), LFEntry.class)) {
                Document fileId = new Document();
                fileId.put("_id", file.getObjectId());
                gridFsTemplate.delete(new BasicQuery(fileId));
            }
        }
    }

}
