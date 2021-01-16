package dev.makarov.bot.rocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;

import static java.net.http.HttpRequest.BodyPublishers.ofString;
import static java.net.http.HttpResponse.BodyHandlers.ofString;

public class RocketApi {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final String POSTFIX = "/api/v1/";
    private static final Logger LOGGER = LogManager.getLogger(RocketApi.class);

    private String url;
    private String authToken;
    private String userId;

    private RocketApi() {

    }

    public void setStatus(String status) {
        RocketStatusRDTO rdto = new RocketStatusRDTO();
        rdto.setStatus("online");
        rdto.setMessage(status);
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = getRequestBuilder("users.setStatus")
                    .POST(ofString(MAPPER.writeValueAsString(rdto), StandardCharsets.UTF_8))
                    .build();
            client.send(request, ofString(StandardCharsets.UTF_8));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private HttpRequest.Builder getRequestBuilder(String method) {
        return HttpRequest.newBuilder()
                .uri(URI.create(url + method))
                .header("X-Auth-Token", authToken)
                .header("X-User-Id", userId)
                .header("Content-Type", "application/json");
    }

    public static Builder newBuilder() {
        return new RocketApi().new Builder();
    }

    public class Builder {

        private Builder() {

        }

        public Builder withUrl(String url) {
            RocketApi.this.url = url + POSTFIX;
            return this;
        }

        public Builder withAuthToken(String authToken) {
            RocketApi.this.authToken = authToken;
            return this;
        }

        public Builder withUserId(String userId) {
            RocketApi.this.userId = userId;
            return this;
        }

        public RocketApi build() {
            return RocketApi.this;
        }
    }

}
