package com.timfralou.app.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import com.timfralou.app.seeds.GenreSeed;

public class GenreTest {
    private JSONObject genreJson;

    public GenreTest() {
        String jsonTxt = new String();
        try {
            jsonTxt = new String(
                    Files.readAllBytes(Paths.get("src/test/java/com/timfralou/app/seeds/jsons/Genre.json")));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        JSONObject jsonObj = new JSONObject(jsonTxt);
        this.genreJson = jsonObj;
    }

    @Test
    public void hasgenre() {
        String actualgenre = genreJson.optString("genre", "");
        Genre genre = new GenreSeed().genre();
        assertEquals(genre.genre(), actualgenre);
    }
}
