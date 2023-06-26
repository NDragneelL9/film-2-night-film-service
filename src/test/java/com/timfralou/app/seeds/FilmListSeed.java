package com.timfralou.app.seeds;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.timfralou.app.models.Film;

public class FilmListSeed {
    private List<Film> filmList;

    public FilmListSeed() {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Film> films = new ArrayList<>();
        try {
            films = objectMapper.readValue(
                    new File("src/test/java/com/timfralou/app/seeds/jsons/FilmList.json"),
                    new TypeReference<ArrayList<Film>>() {
                    });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        this.filmList = films;
    }

    public List<Film> filmList() {
        return filmList;
    }
}
