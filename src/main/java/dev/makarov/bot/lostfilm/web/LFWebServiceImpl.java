package dev.makarov.bot.lostfilm.web;

import com.mongodb.client.gridfs.model.GridFSFile;
import dev.makarov.bot.configuration.WebConfiguration;
import dev.makarov.bot.lostfilm.persistance.entity.LFEntry;
import dev.makarov.bot.lostfilm.persistance.repository.LFEntryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class LFWebServiceImpl implements LFWebService {

    private final WebConfiguration configuration;
    private final LFEntryRepository repository;
    private final GridFsTemplate fsTemplate;

    @Override
    public LFRss getRss(String quality) {
        Page<LFEntry> entries = repository
                .findAll(PageRequest.of(0, 100, Sort.by(
                        Sort.Order.desc("pubDate"),
                        Sort.Order.desc("created")
                )));
        List<LFRssChannelItem> items = new ArrayList<>();
        Instant lastBuildDate = null;
        for (LFEntry entry : entries) {
            if (lastBuildDate == null) {
                lastBuildDate = entry.getCreated();
            }
            items.addAll(entry.getTorrents().stream()
                    .filter(t -> {
                        if (quality == null) {
                            return true;
                        }
                        return quality.equals(t.getQuality());
                    })
                    .map(t -> {
                        String name = t.getName();
                        if (quality != null) {
                            name = name
                                    .replace(". WEB-DLRip", "")
                                    .replace(". 720p WEB-DLRip", "")
                                    .replace(". 1080p WEB-DLRip", "");
                        }
                        return LFRssChannelItem.builder()
                                .title(name)
                                .link(configuration.getDomain() + "/lostfilm/rss/torrent/" + t.getGridFsObjectId())
                                .pubDate(entry.getCreated())
                                .originalUrl(entry.getOriginUrl())
                                .build();
                    })
                    .collect(Collectors.toList()));
        }
        return LFRss.builder()
                .version("1.0")
                .channel(LFRssChannel.builder()
                        .title("Свежачок от LostFilm.TV")
                        .link("https://www.lostfilm.tv/")
                        .lastBuildDate(lastBuildDate)
                        .items(items)
                        .build())
                .build();
    }

    @Override
    public Resource getTorrent(ObjectId id) throws FileNotFoundException {
        GridFSFile file = fsTemplate.findOne(Query.query(Criteria.where("_id").is(id)));
        if (file == null) {
            throw new FileNotFoundException(id.toString());
        }
        return fsTemplate.getResource(file);
    }
}
