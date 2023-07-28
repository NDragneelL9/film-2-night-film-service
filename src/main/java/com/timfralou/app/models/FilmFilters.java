package com.timfralou.app.models;

import java.io.IOException;
import java.sql.Connection;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timfralou.app.interfaces.KinopoiskAPI;

public class FilmFilters {
    private final Connection dbConn;

    public FilmFilters(Connection dbConn) {
        this.dbConn = dbConn;
    }

    public String syncFilmFilters(KinopoiskAPI knpApi) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String filmFilters = knpApi.getFilmFilters();
        JSONObject jsonObj = new JSONObject(filmFilters);
        if (jsonObj.has("message")) {
            return objectMapper.writeValueAsString(jsonObj.getString("message"));
        } else {
            JSONArray jsonArrGenres = jsonObj.getJSONArray("genres");
            Genre[] genres = objectMapper.readValue(jsonArrGenres.toString(), Genre[].class);
            for (Genre genre : genres) {
                genre.saveToDB(dbConn);
            }
            JSONArray jsonArrCountries = jsonObj.getJSONArray("countries");
            Country[] countries = objectMapper.readValue(jsonArrCountries.toString(), Country[].class);
            for (Country country : countries) {
                country.saveToDB(dbConn);
            }
            return filmFilters;
        }
    }
}
