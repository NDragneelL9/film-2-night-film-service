package com.timfralou.app.servlets;

import java.io.*;
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
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.timfralou.app.models.Film;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet(urlPatterns = "/films/top-250")
public class TopFilmsServlet extends HttpServlet {
    private static final String BASE_URL = "https://kinopoiskapiunofficial.tech/";
    private static final String TOP_250_URL = "api/v2.2/films/top?type=TOP_250_BEST_FILMS";
    private static final String PAGE_PARAM = "&page=";
    private static final Dotenv dotenv = Dotenv.configure()
            .directory("/usr/local/tomcat/webapps")
            .filename("env")
            .load();
    private static final int BATCH_SIZE = 50;

    public void doGet(HttpServletRequest servRequest, HttpServletResponse servResponse)
            throws IOException, ServletException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        String API_KEY = dotenv.get("KNPSK_API_KEY");
        String top250url = BASE_URL + TOP_250_URL;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            // to prevent exception when encountering unknown property:
            objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

            int currentPage = 1;
            String jsonFirstPage = getTopFilms(top250url, API_KEY, PAGE_PARAM + currentPage);
            JSONObject jsonObj = new JSONObject(jsonFirstPage);
            int pagesCount = jsonObj.getInt("pagesCount");
            JSONArray jsonFilms = jsonObj.getJSONArray("films");
            List<Film> filmList = objectMapper.readValue(jsonFilms.toString(), new TypeReference<ArrayList<Film>>() {
            });
            currentPage++;

            for (int i = currentPage; i <= pagesCount; i++) {
                String nextFilmsPage = getTopFilms(top250url, API_KEY, PAGE_PARAM + i);
                JSONObject nextJsonObj = new JSONObject(nextFilmsPage);
                JSONArray nextJsonFilms = nextJsonObj.getJSONArray("films");
                List<Film> nextfilmList = objectMapper.readValue(nextJsonFilms.toString(),
                        new TypeReference<ArrayList<Film>>() {
                        });
                filmList = Stream.concat(filmList.stream(), nextfilmList.stream()).collect(Collectors.toList());
            }

            try {
                List<Film> totalFilms = new ArrayList<>(new HashSet<>(filmList));
                saveToDB(totalFilms);
            } catch (SQLException ex) {
                System.out.println(ex);
            }

            servResponse.setCharacterEncoding("UTF-8");
            servResponse.setContentType("application/json");
            String responseJSON = objectMapper.writeValueAsString(filmList);
            PrintWriter out = servResponse.getWriter();
            out.println(responseJSON);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String getTopFilms(String url, String API_KEY, String page) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("X-API-KEY", API_KEY)
                .uri(URI.create(url + page))
                .build();

        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

        return response.body();
    }

    private void saveToDB(List<Film> films) throws SQLException {
        String DBurl = "jdbc:postgresql://PostgreSQL/film2night";
        String DBuser = dotenv.get("PSQL_F2N_USER");
        String DBpass = dotenv.get("PSQL_F2N_PWD");

        Connection conn = DriverManager.getConnection(DBurl, DBuser, DBpass);
        conn.setAutoCommit(false);
        try (PreparedStatement pstmt = conn.prepareStatement(
                "INSERT INTO films (\"kinopoiskId\", \"nameRu\", \"ratingKinopoisk\", \"ratingKinopoiskVoteCount\", \"year\", \"filmLength\") "
                        +
                        "VALUES (?, ?, ?, ?, ? ,?)")) {
            for (int i = 0; i < films.size(); i++) {
                pstmt.setInt(1, films.get(i).kinopoiskId());
                pstmt.setString(2, films.get(i).nameRu());
                pstmt.setString(3, films.get(i).ratingKinopoisk());
                pstmt.setInt(4, films.get(i).ratingKinopoiskVoteCount());
                pstmt.setInt(5, films.get(i).year());
                pstmt.setInt(6, films.get(i).filmLength());
                pstmt.addBatch();
                if (i + 1 % BATCH_SIZE == 0 || i == films.size() - 1) {
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