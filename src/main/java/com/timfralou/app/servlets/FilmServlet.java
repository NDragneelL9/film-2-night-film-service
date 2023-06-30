package com.timfralou.app.servlets;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timfralou.app.api.KinopoiskAPI;
import com.timfralou.app.models.Film;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet(urlPatterns = "/films/*")
public class FilmServlet extends BaseServlet {

    public void doGet(HttpServletRequest servRequest, HttpServletResponse servResponse)
            throws IOException, ServletException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        ObjectMapper objectMapper = new ObjectMapper();

        KinopoiskAPI knpApi = new KinopoiskAPI();
        String pathInfo = servRequest.getPathInfo();
        String filmJson = knpApi.getFilm(pathInfo);
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
    }

    private void updateFilm(Film film) throws SQLException {
        String DBurl = "jdbc:postgresql://PostgreSQL/film2night";
        String DBuser = super.dotenv().get("PSQL_F2N_USER");
        String DBpass = super.dotenv().get("PSQL_F2N_PWD");

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
