package dev.makarov.bot.telegram.lostfilm.dto;

public class LFFeed {

    private String version;
    private LFChannel channel;

    public String getVersion() {
        return version;
    }

    public LFFeed setVersion(String version) {
        this.version = version;
        return this;
    }

    public LFChannel getChannel() {
        return channel;
    }

    public LFFeed setChannel(LFChannel channel) {
        this.channel = channel;
        return this;
    }
}
