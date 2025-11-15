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

    private static String lastApiMessage = null;

    public apiBase(IJsonObjectMapper<T> mapper) {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(TIMEOUT_DURATION)
                .build();
        this.mapper = mapper;
    }

    public static String getLastApiMessage() {
        String message = lastApiMessage;
        lastApiMessage = null;
        return message;
    }

    public abstract String getBaseURL();

    public List<T> executeGetList() {
        lastApiMessage = null;
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
                lastApiMessage = "LOG: Requisição à API "+ url +" excedeu 3s: "+ durationMs +"ms";
            }

            if (response.statusCode() == 200) {
                return mapper.mapJsonToList(response.body());
            } else {
                lastApiMessage = "Falha HTTP na API "+ url +". Status: "+ response.statusCode();
                return Collections.emptyList();
            }
        } catch (IOException | InterruptedException e) {
            lastApiMessage = "Erro de comunicação com a API "+ url +": "+ e.getMessage();

            if (e instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
            return Collections.emptyList();
        }
    }
}
