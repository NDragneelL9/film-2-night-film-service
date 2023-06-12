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

import org.json.JSONArray;
import org.json.JSONObject;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.timfralou.models.Film;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet(urlPatterns = "/films/top-250")
public class TopFilmsServlet extends HttpServlet {
    private static final String BASE_URL = "https://kinopoiskapiunofficial.tech/";
    private static final String TOP_250_URL = "api/v2.2/films/top?type=TOP_250_BEST_FILMS";
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
        String top250url = BASE_URL + TOP_250_URL;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            // to prevent exception when encountering unknown property:
            objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            String jsonSTR = getTopFilms(top250url, API_KEY);
            JSONObject json = new JSONObject(jsonSTR);
            JSONArray jsonFilms = json.getJSONArray("films");

            Film[] filmList = objectMapper.readValue(jsonFilms.toString(), Film[].class);

            try {
                saveToDB(filmList);
            } catch (SQLException ex) {
                System.out.println(ex);
            }

            servResponse.setCharacterEncoding("UTF-8");
            servResponse.setContentType("application/json");
            PrintWriter out = servResponse.getWriter();
            out.println(json.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String getTopFilms(String url, String API_KEY) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("X-API-KEY", API_KEY)
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

        return response.body();
    }

    private void saveToDB(Film[] films) throws SQLException {
        String DBurl = "jdbc:postgresql://PostgreSQL/film2night";
        String DBuser = dotenv.get("PSQL_F2N_USER");
        String DBpass = dotenv.get("PSQL_F2N_PWD");

        Connection conn = DriverManager.getConnection(DBurl, DBuser, DBpass);
        conn.setAutoCommit(false);
        try (PreparedStatement pstmt = conn.prepareStatement(
                "INSERT INTO films (\"kinopoiskId\", \"nameRu\", \"ratingKinopoisk\", \"ratingKinopoiskVoteCount\", \"year\", \"filmLength\") "
                        +
                        "VALUES (?, ?, ?, ?, ? ,?)")) {
            for (int i = 0; i < films.length; i++) {
                pstmt.setInt(1, films[i].kinopoiskId());
                pstmt.setString(2, films[i].nameRu());
                pstmt.setFloat(3, films[i].ratingKinopoisk());
                pstmt.setInt(4, films[i].ratingKinopoiskVoteCount());
                pstmt.setInt(5, films[i].year());
                pstmt.setInt(6, films[i].filmLength());
                pstmt.addBatch();
                if (i + 1 % BATCH_SIZE == 0 || i == films.length - 1) {
                    try {
                        int[] result = pstmt.executeBatch();
                        conn.commit();
                        System.out.println(result);
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