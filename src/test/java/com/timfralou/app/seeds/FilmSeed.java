package com.timfralou.app.seeds;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timfralou.app.models.Film;

public class FilmSeed {
    private Film film;
    private Film filmInHHMM;

    public FilmSeed() {
        ObjectMapper objectMapper = new ObjectMapper();
        Film film = new Film();
        Film filmInHHMM = new Film();
        String basePath = "src/test/java/com/timfralou/app/seeds/jsons/";
        try {
            film = objectMapper.readValue(
                    new File(basePath + "Film.json"), Film.class);
            filmInHHMM = objectMapper.readValue(new File(basePath + "FilmInHHMM.json"), Film.class);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        this.film = film;
        this.filmInHHMM = filmInHHMM;
    }

    public Film film() {
        return film;
    }

    public Film filmInHHMM() {
        return filmInHHMM;
    }
}
