package com.timfralou.app.models;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timfralou.app.interfaces.KinopoiskAPI;

public class TopFilms {
    private final Connection dbConn;
    private List<Film> filmList = new ArrayList<Film>();

    public TopFilms(Connection dbConn) {
        this.dbConn = dbConn;
    }

    public List<Film> pgFilmList() {
        try {
            PreparedStatement pstmt = dbConn.prepareStatement("SELECT * from films;");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                filmList.add(pgFilm(rs));
            }
            return filmList;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return new ArrayList<>();
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
        String lastSync = rs.getString("lastSync");
        int kinopoiskId = rs.getInt("kinopoiskId");
        Film film = new Film(nameRu, ratingKinopoisk,
                ratingKinopoiskVoteCount, year,
                filmLength, imdbId, nameEn,
                nameOriginal, posterUrl, reviewsCount,
                ratingImdb, ratingImdbVoteCount,
                webUrl, description, type,
                ratingMpaa, ratingAgeLimits,
                hasImax, has3D, lastSync, kinopoiskId);
        return film;
    }

    public String loadPosters() {
        this.pgFilmList();
        if (filmList.isEmpty()) {
            return "No films to download posters";
        }
        for (Film film : filmList) {
            film.downloadPoster();
        }
        return "Posters successfully downloaded";
    }

    public String syncTopFilms(KinopoiskAPI knpApi) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String topFilmsJSON = "";
        String jsonFilmsPage = knpApi.getFilmsPage(1);
        JSONObject jsonObj = new JSONObject(jsonFilmsPage);
        if (jsonObj.has("message")) {
            topFilmsJSON = objectMapper.writeValueAsString(jsonObj.getString("message"));
        } else {
            int pagesCount = jsonObj.getInt("pagesCount");
            List<Film> topFilms = new ArrayList<Film>();
            for (int i = 1; i <= pagesCount; i++) {
                String filmsPage = knpApi.getFilmsPage(i);
                JSONObject filmJSON = new JSONObject(filmsPage);
                JSONArray jsonFilms = filmJSON.getJSONArray("films");
                Film[] films = objectMapper.readValue(jsonFilms.toString(), Film[].class);
                for (Film film : films) {
                    film.saveToDB(dbConn);
                }
                topFilms = Stream.concat(topFilms.stream(), Arrays.stream(films)).collect(Collectors.toList());
                topFilmsJSON = objectMapper.writeValueAsString(topFilms);
            }
        }
        return topFilmsJSON;
    }
}
