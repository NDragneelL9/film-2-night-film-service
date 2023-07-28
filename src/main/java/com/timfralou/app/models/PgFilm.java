package com.timfralou.app.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class PgFilm {
    private final Connection dbConn;
    private Film film;

    public PgFilm(Connection dbConn) {
        this.dbConn = dbConn;
    }

    public Film pgFilmById(int knpId) {
        try {
            PreparedStatement pstmt = dbConn
                    .prepareStatement("SELECT * FROM films where \"kinopoiskId\" = ?");
            pstmt.setInt(1, knpId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                this.film = pgToFilm(rs);
            }
            return film;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return new Film();
        }
    }

    public Film pgToFilm(ResultSet rs) throws SQLException {
        String nameRu = rs.getString("nameRu");
        String ratingKinopoisk = rs.getString("ratingKinopoisk");
        int ratingKinopoiskVoteCount = rs.getInt("ratingKinopoiskVoteCount");
        int year = rs.getInt("year");
        int filmLength = rs.getInt("filmLength");
        String imdbId = rs.getString("imdbId");
        String nameEn = rs.getString("nameEn");
        String nameOriginal = rs.getString("nameOriginal");
        String posterUrl = rs.getString("posterUrl");
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
        Timestamp lastSync = rs.getTimestamp("lastSync");
        int kinopoiskId = rs.getInt("kinopoiskId");
        Boolean banned = rs.getBoolean("banned");
        Film film = new Film(nameRu, ratingKinopoisk,
                ratingKinopoiskVoteCount, year,
                filmLength, imdbId, nameEn,
                nameOriginal, posterUrl, reviewsCount,
                ratingImdb, ratingImdbVoteCount,
                webUrl, description, type,
                ratingMpaa, ratingAgeLimits,
                hasImax, has3D, lastSync, kinopoiskId, banned);
        return film;
    }
}
