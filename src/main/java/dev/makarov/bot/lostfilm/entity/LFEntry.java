package dev.makarov.bot.lostfilm.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LFEntry {

    @Id
    private ObjectId id;
    private String name;
    private List<LFEntryTorrent> torrents;
    private Instant created;

}
