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
            PreparedStatement pstmt = dbConn
                    .prepareStatement(
                            "SELECT * FROM films where \"banned\" = false ORDER BY \"ratingKinopoisk\" DESC LIMIT 250;");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                filmList.add(new PgFilm(dbConn).pgToFilm(rs));
            }
            return filmList;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return new ArrayList<>();
        }
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
