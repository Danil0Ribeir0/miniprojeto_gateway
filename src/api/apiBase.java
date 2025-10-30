package api;

import api.service.IJsonObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.List;

public abstract class apiBase<T> {
    private static final Duration TIMEOUT_DURATION = Duration.ofSeconds(3);
    private final HttpClient httpClient;
    private final IJsonObjectMapper<T> mapper;

    public apiBase(IJsonObjectMapper<T> mapper) {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(TIMEOUT_DURATION)
                .build();
        this.mapper = mapper;
    }

    public abstract String getBaseURL();

    public List<T> executeGetList() {
        Instant startTime = Instant.now();
        String url = getBaseURL();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(TIMEOUT_DURATION)
                .GET()
                .build();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            long durationMs = Duration.between(startTime, Instant.now()).toMillis();

            if (durationMs > TIMEOUT_DURATION.toMillis()) {
                System.out.println("LOG: Requisição à API "+ url +" excedeu 3s: "+ durationMs +"ms");
            }

            if (response.statusCode() == 200) {
                return mapper.mapJsonToList(response.body());
            } else {
                System.err.println("Falha HTTP na API "+ url +". Status: "+ response.statusCode());
                return Collections.emptyList();
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Erro de comunicação com a API "+ url +": "+ e.getMessage());

            if (e instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
            return Collections.emptyList();
        }
    }
}
