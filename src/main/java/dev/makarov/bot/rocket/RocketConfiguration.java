package dev.makarov.bot.rocket;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "rocket")
public class RocketConfiguration {

    private final Boolean enabled;
    private final String url;
    private final String authToken;
    private final String userId;

    public RocketConfiguration(Boolean enabled, String url, String authToken, String userId) {
        this.enabled = enabled;
        this.url = url;
        this.authToken = authToken;
        this.userId = userId;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public String getUrl() {
        return url;
    }

    public String getAuthToken() {
        return authToken;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "RocketConfiguration{" +
                "enabled=" + enabled +
                ", url='" + url + '\'' +
                ", authToken='" + authToken + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
