package dev.makarov.bot.lostfilm.persistance;

import dev.makarov.bot.lostfilm.dto.LFParsedItem;
import dev.makarov.bot.lostfilm.persistance.entity.LFEntry;
import dev.makarov.bot.lostfilm.persistance.entity.LFEntryTorrent;
import dev.makarov.bot.lostfilm.persistance.repository.LFEntryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.stream.Collectors;

@Slf4j
@Profile("!test")
@Service
@RequiredArgsConstructor
public class LFPersisterImpl implements LFPersister {

    private final LFEntryRepository repository;
    private final GridFsTemplate gridFsTemplate;

    @Override
    public void persist(LFParsedItem item) {
        if (repository.existsByName(item.getName())) {
            return;
        }
        LFEntry entry = LFEntry.builder()
                .id(ObjectId.get())
                .name(item.getName())
                .torrents(item.getTorrents().stream()
                        .map(pt -> LFEntryTorrent.builder()
                                .id(ObjectId.get())
                                .name(pt.getName())
                                .quality(pt.getQuality())
                                .url(pt.getTorrentUrl())
                                .gridFsObjectId(gridFsTemplate.store(
                                        new ByteArrayInputStream(pt.getTorrentFile()),
                                        pt.getTorrentUrl()))
                                .build())
                        .collect(Collectors.toList()))
                .created(item.getCreated())
                .pubDate(item.getPubDate())
                .originUrl(item.getOriginUrl())
                .build();
        repository.save(entry);
        log.debug("LFEntry {} persisted", item.getName());
    }

}
