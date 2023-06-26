package com.timfralou.app.seeds;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timfralou.app.models.Genre;

public class GenreSeed {
    private Genre genre;

    public GenreSeed() {
        ObjectMapper objectMapper = new ObjectMapper();
        Genre genre = new Genre();
        try {
            genre = objectMapper.readValue(
                    new File("src/test/java/com/timfralou/app/seeds/jsons/Genre.json"), Genre.class);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        this.genre = genre;
    }

    public Genre genre() {
        return genre;
    }
}
