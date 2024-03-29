package com.timfralou.app.api;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import com.timfralou.app.interfaces.KinopoiskAPI;
import io.github.cdimascio.dotenv.Dotenv;

public class knpAPI implements KinopoiskAPI {
    private final KinopoiskUrls urls = new KinopoiskUrls();
    private final String API_KEY;

    public knpAPI(Dotenv dotenv) {
        this.API_KEY = dotenv.get("KNPSK_API_KEY");
    }

    public String getFilmsPage(int page) {
        return sendGETRequest(urls.topFilmsURL() + page);
    }

    public String getFilmFilters() {
        return sendGETRequest(urls.filmFiltersURL());
    }

    public String getFilm(String filmId) {
        return sendGETRequest(urls.filmURL() + filmId);
    }

    private String sendGETRequest(String url) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .header("X-API-KEY", API_KEY)
                    .uri(URI.create(url))
                    .build();
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            return response.body();
        } catch (InterruptedException | IOException ex) {
            ex.printStackTrace();
            return "Error has happened: " + ex.getMessage();
        }
    }

    @Override
    public String toString() {
        return "knpAPI [urls=" + urls + ", methods=" + this.getClass().getDeclaredMethods() + "]";
    }
}
