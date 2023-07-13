package com.timfralou.app.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import com.timfralou.app.BasicTest;
import com.timfralou.app.seeds.FilmSeed;

public class FilmTest extends BasicTest {
    private Film film = new FilmSeed().film();

    @Test
    public void convertsFilmLenght() {
        Film film = new FilmSeed().filmInHHMM();
        boolean correctFilmLength = film.toString().contains("filmLength=136");
        assertEquals(true, correctFilmLength);
    }

    @Test
    public void writesToString() {
        boolean printsToString = film.toString().startsWith("Film [kinopoiskId=");
        assertEquals(true, printsToString);
    }

    @Test
    public void savesFilmToDB() throws SQLException {
        int insertedrows = film.saveToDB(dbTEST.connect());
        assertEquals(1, insertedrows);
    }

    @Test
    public void updatesFilmInDB() throws SQLException {
        film.saveToDB(dbTEST.connect());
        int affectedrows = film.updateInDB(dbTEST.connect());
        assertEquals(1, affectedrows);
    }

    @AfterAll
    public static void cleanUp() throws SQLException {
        // TODO:
        // film.delete() - deletes from db
        dbTEST.updateQuery("DELETE FROM films");
    }
}
