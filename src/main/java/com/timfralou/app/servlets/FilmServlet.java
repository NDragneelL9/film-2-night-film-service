package com.timfralou.app.servlets;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.timfralou.app.models.Film;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet(urlPatterns = "/films/*")
public class FilmServlet extends HttpServlet {
    private static final String BASE_URL = "https://kinopoiskapiunofficial.tech/";
    private static final String FILM_URL = "api/v2.2/films/";
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

        String pathInfo = servRequest.getPathInfo();
        String[] pathParts = pathInfo.split("/");
        String filmId = pathParts[1];
        String url = BASE_URL + FILM_URL + filmId;
        String API_KEY = dotenv.get("KNPSK_API_KEY");
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            // to prevent exception when encountering unknown property:
            objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

            String filmJson = getFilm(url, API_KEY);
            Film film = objectMapper.readValue(filmJson, Film.class);
            try {
                updateFilm(film);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            servResponse.setCharacterEncoding("UTF-8");
            servResponse.setContentType("text/html");
            PrintWriter out = servResponse.getWriter();
            out.println(filmJson);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String getFilm(String url, String API_KEY) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("X-API-KEY", API_KEY)
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

        return response.body();
    }

    private void updateFilm(Film film) throws SQLException {
        String DBurl = "jdbc:postgresql://PostgreSQL/film2night";
        String DBuser = dotenv.get("PSQL_F2N_USER");
        String DBpass = dotenv.get("PSQL_F2N_PWD");

        Connection conn = DriverManager.getConnection(DBurl, DBuser, DBpass);
        try (PreparedStatement pstmt = conn.prepareStatement(
                "UPDATE films SET \"imdbId\" = ?, \"nameEn\" = ?, \"nameOriginal\" = ?, \"reviewsCount\" = ?, " +
                        "\"ratingImdb\" = ?, \"ratingImdbVoteCount\" = ?, \"webUrl\" = ?, \"description\" = ?, \"type\" = ?, "
                        +
                        "\"ratingMpaa\" = ?, \"ratingAgeLimits\" = ?, \"hasImax\" = ?, \"has3D\" = ?, \"lastSync\" = ? "
                        +
                        "WHERE \"kinopoiskId\" = ?")) {
            pstmt.setString(1, film.imdbId());
            pstmt.setString(2, film.nameEn());
            pstmt.setString(3, film.nameOriginal());
            pstmt.setInt(4, film.reviewsCount());
            pstmt.setString(5, film.ratingImdb());
            pstmt.setInt(6, film.ratingImdbVoteCount());
            pstmt.setString(7, film.webUrl());
            pstmt.setString(8, film.description());
            pstmt.setObject(9, film.type(), java.sql.Types.OTHER);
            pstmt.setString(10, film.ratingMpaa());
            pstmt.setString(11, film.ratingAgeLimits());
            pstmt.setBoolean(12, film.hasImax());
            pstmt.setBoolean(13, film.has3D());
            pstmt.setString(14, film.lastSync());
            pstmt.setInt(15, film.kinopoiskId());
            pstmt.executeUpdate();
        }
        conn.close();
    }
}
