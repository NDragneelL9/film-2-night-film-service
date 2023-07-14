package com.timfralou.app.api;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import io.github.cdimascio.dotenv.Dotenv;

public class KinopoiskAPI {
    private final KinopoiskUrls urls;
    private final String API_KEY;

    public KinopoiskAPI(Dotenv dotenv) {
        this.urls = new KinopoiskUrls();
        this.API_KEY = dotenv.get("KNPSK_API_KEY");
    }

    public String getFilmsPage(int page) {
        return sendGETRequest(urls.topFilmsURL() + page);
    }

    public String getFilmFilters() {
        return sendGETRequest(urls.filmFiltersURL());
    }

    public String getFilm(String pathInfo) {
        String[] pathParts = pathInfo.split("/");
        String filmId = pathParts[1];
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

}
