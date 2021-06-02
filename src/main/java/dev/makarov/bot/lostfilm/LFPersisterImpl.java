package dev.makarov.bot.lostfilm;

import dev.makarov.bot.lostfilm.dto.LFParsedItem;
import dev.makarov.bot.lostfilm.entity.LFEntry;
import dev.makarov.bot.lostfilm.entity.LFEntryTorrent;
import dev.makarov.bot.lostfilm.repository.LFEntryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.Optional;
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
        Optional<LFEntry> entryOptional = repository.findByName(item.getName());
        if (entryOptional.isPresent()) {
            log.info("LFEntry {} already present", item.getName());
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
                .build();
        repository.save(entry);
        log.debug("LFEntry {} persisted", item.getName());
    }

}
