package dev.makarov.bot.rocket;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.model_objects.IPlaylistItem;
import com.wrapper.spotify.model_objects.miscellaneous.CurrentlyPlaying;
import com.wrapper.spotify.model_objects.specification.ArtistSimplified;
import com.wrapper.spotify.model_objects.specification.Track;
import dev.makarov.bot.spotify.SpotifyConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Service
@ConditionalOnProperty(
        value = "rocket.enabled",
        havingValue = "true",
        matchIfMissing = true
)
public class RocketBot {

    private static final Logger LOGGER = LogManager.getLogger(RocketBot.class);

    private final RocketApi rocketApi;
    private final SpotifyApi spotifyApi;

    public RocketBot(
            RocketConfiguration rocketConfiguration,
            SpotifyConfiguration spotifyConfiguration) {

        this.rocketApi = RocketApi.newBuilder()
                .withUrl(rocketConfiguration.getUrl())
                .withUserId(rocketConfiguration.getUserId())
                .withAuthToken(rocketConfiguration.getAuthToken())
                .build();

        this.spotifyApi = SpotifyApi.builder()
                .setAccessToken(spotifyConfiguration.getAccessToken())
                .build();
    }

    @PostConstruct
    public void postConstruct() {
        LOGGER.info("RocketBot loaded");
    }

    @Scheduled(fixedDelay = 10_000)
    public void scheduleFixedDelayTask() {
        try {
            CurrentlyPlaying currentlyPlaying = spotifyApi.getUsersCurrentlyPlayingTrack().build().execute();
            if (currentlyPlaying != null && currentlyPlaying.getIs_playing()) {
                IPlaylistItem item = currentlyPlaying.getItem();
                if (item instanceof Track) {
                    Track track = (Track) item;
                    String songName = track.getName();
                    String artists = String.join(
                            ", ",
                            Arrays.stream(track.getArtists()).map(ArtistSimplified::getName).toArray(CharSequence[]::new));
                    String status = String.format("%s %s - %s", RocketSongEmotion.getEmotion(songName), artists, songName);
                    rocketApi.setStatus(status);
                    LOGGER.debug("Статус обновлен на {}", status);
                }
            } else {
                rocketApi.setStatus("");
                LOGGER.debug("Статус сброшен");
            }

        } catch (Exception e) {
            LOGGER.error("Что-то пошло не так с обновлением статуса", e);
        }
    }

}
