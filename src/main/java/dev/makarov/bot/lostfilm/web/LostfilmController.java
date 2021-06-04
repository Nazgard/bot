package dev.makarov.bot.lostfilm.web;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "lostfilm/rss")
public class LostfilmController {

    private final LFWebService service;

    @GetMapping(produces = "application/xml")
    public ResponseEntity<LFRss> rss(@RequestParam(name = "quality", required = false) String quality) {
        return ResponseEntity.ok(service.getRss(quality));
    }

    @GetMapping(path = "torrent/{id}", produces = "application/torrent")
    public ResponseEntity<Resource> torrent(@PathVariable(name = "id") ObjectId id) throws FileNotFoundException {
        return ResponseEntity
                .ok()
                .header("Content-Disposition", "attachment; filename=" + id + ".torrent")
                .body(service.getTorrent(id));
    }

}
