package dev.makarov.bot.lostfilm.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LFEntryTorrent {

    @Id
    private ObjectId id;
    private String name;
    private String quality;
    private String url;
    private ObjectId gridFsObjectId;

}
