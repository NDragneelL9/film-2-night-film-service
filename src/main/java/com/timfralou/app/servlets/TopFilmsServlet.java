package com.timfralou.app.servlets;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.timfralou.app.models.Film;
import com.timfralou.app.models.FilmList;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet(urlPatterns = "/films/top-250")
public class TopFilmsServlet extends BaseServlet {
    private static final String TOP_250_URL = "api/v2.2/films/top?type=TOP_250_BEST_FILMS";
    private static final String PAGE_PARAM = "&page=";

    public void doGet(HttpServletRequest servRequest, HttpServletResponse servResponse)
            throws IOException, ServletException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        String API_KEY = dotenv.get("KNPSK_API_KEY");
        String filmsUrl = BASE_URL + TOP_250_URL + PAGE_PARAM;
        ObjectMapper objectMapper = new ObjectMapper();

        String jsonPagesCount = getFilmsPage(API_KEY, filmsUrl, 1);
        JSONObject jsonObj = new JSONObject(jsonPagesCount);
        int pagesCount = jsonObj.getInt("pagesCount");

        FilmList topFilms = new FilmList(new ArrayList<Film>());
        for (int i = 1; i <= pagesCount; i++) {
            List<Film> nextfilmList = getFilmsList(objectMapper, API_KEY, filmsUrl, i);
            topFilms = new FilmList(
                    Stream.concat(topFilms.filmList().stream(), nextfilmList.stream()).collect(Collectors.toList()));
        }

        try {
            topFilms.saveToDB("MAIN");
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        String responseJSON = objectMapper.writeValueAsString(topFilms.filmList());
        handleResponse(servResponse, responseJSON);
    }

    private List<Film> getFilmsList(ObjectMapper objectMapper, String API_KEY, String filmsUrl, int i) {
        try {
            String filmsPage = getFilmsPage(API_KEY, filmsUrl, i);
            JSONObject jsonObj = new JSONObject(filmsPage);
            JSONArray jsonFilms = jsonObj.getJSONArray("films");
            List<Film> nextfilmList = objectMapper.readValue(jsonFilms.toString(),
                    new TypeReference<ArrayList<Film>>() {
                    });
            return nextfilmList;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return new ArrayList<Film>();
    }

    private String getFilmsPage(String API_KEY, String url, int page) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .header("X-API-KEY", API_KEY)
                    .uri(URI.create(url + page))
                    .build();
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            return response.body();
        } catch (InterruptedException | IOException ex) {
            ex.printStackTrace();
            return "";
        }
    }

    private void handleResponse(HttpServletResponse servResponse, String responseJSON) {
        try {
            servResponse.setCharacterEncoding("UTF-8");
            servResponse.setContentType("application/json");
            PrintWriter out = servResponse.getWriter();
            out.println(responseJSON);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}