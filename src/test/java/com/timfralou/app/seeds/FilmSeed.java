package com.timfralou.app.seeds;

import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.timfralou.app.models.Film;

public class FilmSeed {
    private Film film;

    public FilmSeed() {
        ObjectMapper objectMapper = new ObjectMapper();
        Film film = new Film();
        try {
            film = objectMapper.readValue(
                    new File("src/test/java/com/timfralou/app/seeds/Film.json"), Film.class);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        this.film = film;
    }

    public Film film() {
        return film;
    }
}
