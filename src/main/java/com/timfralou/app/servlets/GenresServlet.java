package com.timfralou.app.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.timfralou.app.models.Film;
import com.timfralou.app.models.Genre;

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
    private static final int BATCH_SIZE = 20;

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
            List<Genre> genresList = objectMapper.readValue(jsonGenres.toString(),
                    new TypeReference<ArrayList<Genre>>() {
                    });
            try {
                saveToDB(genresList);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
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

    private void saveToDB(List<Genre> genres) throws SQLException {
        String DBurl = "jdbc:postgresql://PostgreSQL/film2night";
        String DBuser = dotenv.get("PSQL_F2N_USER");
        String DBpass = dotenv.get("PSQL_F2N_PWD");

        Connection conn = DriverManager.getConnection(DBurl, DBuser, DBpass);
        conn.setAutoCommit(false);
        try (PreparedStatement pstmt = conn.prepareStatement(
                "INSERT INTO genres (\"genre\") "
                        +
                        "VALUES (?)")) {
            for (int i = 0; i < genres.size(); i++) {
                pstmt.setString(1, genres.get(i).genre());
                pstmt.addBatch();
                if (i + 1 % BATCH_SIZE == 0 || i == genres.size() - 1) {
                    try {
                        pstmt.executeBatch();
                        conn.commit();
                    } catch (BatchUpdateException ex) {
                        System.out.println(ex);
                        conn.rollback();
                    }
                }
            }
        }
        conn.close();
    }
}
