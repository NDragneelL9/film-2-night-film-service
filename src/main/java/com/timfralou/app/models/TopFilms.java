package com.timfralou.app.models;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TopFilms {
    private final Connection dbConn;
    private List<Film> filmList = new ArrayList<Film>();

    public TopFilms(Connection dbConn) {
        this.dbConn = dbConn;
    }

    public String filmList() {
        try {
            PreparedStatement pstmt = dbConn.prepareStatement("SELECT * from films;");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                filmList.add(pgFilm(rs));
            }
            ObjectMapper objMapper = new ObjectMapper();
            String responseJSON = objMapper.writeValueAsString(filmList);
            return responseJSON;
        } catch (IOException | SQLException ex) {
            ex.printStackTrace();
            return ex.getMessage();
        }
    }

    private Film pgFilm(ResultSet rs) throws SQLException {
        String nameRu = rs.getString("nameRu");
        String ratingKinopoisk = rs.getString("ratingKinopoisk");
        int ratingKinopoiskVoteCount = rs.getInt("ratingKinopoiskVoteCount");
        int year = rs.getInt("year");
        int filmLength = rs.getInt("filmLength");
        String imdbId = rs.getString("imdbId");
        String nameEn = rs.getString("nameEn");
        String nameOriginal = rs.getString("nameOriginal");
        int reviewsCount = rs.getInt("reviewsCount");
        String ratingImdb = rs.getString("ratingImdb");
        int ratingImdbVoteCount = rs.getInt("ratingImdbVoteCount");
        String webUrl = rs.getString("webUrl");
        String description = rs.getString("description");
        String type = rs.getString("type") != null ? rs.getString("type") : filmType.UNKNOWN.toString();
        String ratingMpaa = rs.getString("ratingMpaa");
        String ratingAgeLimits = rs.getString("ratingAgeLimits");
        Boolean hasImax = rs.getBoolean("hasImax");
        Boolean has3D = rs.getBoolean("has3D");
        String lastSync = rs.getString("lastSync");
        int kinopoiskId = rs.getInt("kinopoiskId");
        Film film = new Film(nameRu, ratingKinopoisk,
                ratingKinopoiskVoteCount, year,
                filmLength, imdbId, nameEn,
                nameOriginal, reviewsCount,
                ratingImdb, ratingImdbVoteCount,
                webUrl, description, type,
                ratingMpaa, ratingAgeLimits,
                hasImax, has3D, lastSync, kinopoiskId);
        return film;
    }
}
