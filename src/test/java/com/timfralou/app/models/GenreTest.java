package com.timfralou.app.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;

import org.json.JSONObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import com.timfralou.app.postgresql.PostgreDB;
import com.timfralou.app.postgresql.dbType;
import com.timfralou.app.seeds.GenreSeed;

public class GenreTest {
    private JSONObject genreJson;
    private Genre genre;
    private static final PostgreDB db = new PostgreDB(dbType.TEST);

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
        this.genre = new GenreSeed().genre();
    }

    @Test
    public void hasGenre() {
        String actualgenre = genreJson.optString("genre", "");
        assertEquals(genre.genre(), actualgenre);
    }

    @Test
    public void savesGenreToDB() throws SQLException {
        int insertedRows = genre.saveToDB(db.conn());
        assertEquals(1, insertedRows);
    }

    @AfterAll
    public static void cleanUp() throws SQLException {
        db.updateQuery("DELETE FROM genres");
    }
}
