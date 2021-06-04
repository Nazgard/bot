package dev.makarov.bot.lostfilm.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class LFRestTemplate extends RestTemplate {

    private final LFConfiguration configuration;

    @PostConstruct
    public void postConstruct() {
        setInterceptors(Collections.singletonList(new CookieInterceptor(configuration)));
    }

    @RequiredArgsConstructor
    private static class CookieInterceptor implements ClientHttpRequestInterceptor {

        private final LFConfiguration configuration;

        @Override
        public ClientHttpResponse intercept(
                HttpRequest request,
                byte[] body,
                ClientHttpRequestExecution execution) throws IOException {

            HttpHeaders headers = request.getHeaders();
            headers.add("Cookie", configuration.getCookie());
            return execution.execute(request, body);
        }
    }
}
