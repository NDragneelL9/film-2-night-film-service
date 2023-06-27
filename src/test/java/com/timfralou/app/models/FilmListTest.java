package com.timfralou.app.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import com.timfralou.app.postgresql.PostgreDB;
import com.timfralou.app.seeds.FilmListSeed;

public class FilmListTest {

    @Test
    public void hasFilms() {
        List<Film> films = new ArrayList<Film>(new FilmListSeed().filmList());
        FilmList filmList = new FilmList(films);
        assertEquals(films, filmList.filmList());
    }

    @Test
    public void saveFilmsToDB() throws SQLException {
        String DB_TYPE = "TEST";
        FilmList filmList = new FilmList(new FilmListSeed().filmList());
        assertEquals("Succesfully saved to " + DB_TYPE, filmList.saveToDB(DB_TYPE));
    }

    @AfterAll
    public static void cleanUp() throws SQLException {
        PostgreDB db = new PostgreDB("TEST");
        db.updateQuery("DELETE FROM films");
    }
}
