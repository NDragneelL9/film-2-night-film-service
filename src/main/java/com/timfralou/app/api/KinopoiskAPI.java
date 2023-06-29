package com.timfralou.app.api;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import io.github.cdimascio.dotenv.Dotenv;

public class KinopoiskAPI {
    private final String BASE_URL = "https://kinopoiskapiunofficial.tech/";
    private final String TOP_250_URL = "api/v2.2/films/top?type=TOP_250_BEST_FILMS";
    private final String FILTERS_URL = "api/v2.2/films/filters";
    private final String FILM_URL = "api/v2.2/films/";
    private final String PAGE_PARAM = "&page=";
    private final Dotenv dotenv = Dotenv.configure()
            .directory("/usr/local/")
            .filename("env")
            .load();
    private final String API_KEY = dotenv.get("KNPSK_API_KEY");

    public String getFilmsPage(int page) {
        String url = BASE_URL + TOP_250_URL + PAGE_PARAM + page;
        return sendRequest(url);
    }

    public String getFilmFilters() {
        String url = BASE_URL + FILTERS_URL;
        return sendRequest(url);
    }

    public String getFilm(String pathInfo) {
        String[] pathParts = pathInfo.split("/");
        String filmId = pathParts[1];
        String url = BASE_URL + FILM_URL + filmId;
        return sendRequest(url);
    }

    private String sendRequest(String url) {
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
