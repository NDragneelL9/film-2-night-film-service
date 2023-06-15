package com.timfralou.app.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/genres")
public class GenresServlet extends HttpServlet {
    private static final String BASE_URL = "https://kinopoiskapiunofficial.tech/";
    private static final String FILTERS_URL = "api/v2.2/films/filters";
    private static final Dotenv dotenv = Dotenv.configure()
            .directory("/usr/local/tomcat/webapps")
            .filename("env")
            .load();

    public void doGet(HttpServletRequest servRequest, HttpServletResponse servResponse)
            throws IOException, ServletException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        String API_KEY = dotenv.get("KNPSK_API_KEY");
        String url = BASE_URL + FILTERS_URL;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            // to prevent exception when encountering unknown property:
            objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

            String jsonFilters = getFilmFilters(url, API_KEY);
            JSONObject jsonFiltersObj = new JSONObject(jsonFilters);
            JSONArray jsonGenres = jsonFiltersObj.getJSONArray("genres");

            servResponse.setCharacterEncoding("UTF-8");
            servResponse.setContentType("text/html");

            PrintWriter out = servResponse.getWriter();
            out.println(jsonGenres.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String getFilmFilters(String url, String API_KEY) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("X-API-KEY", API_KEY)
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

        return response.body();
    }
}
