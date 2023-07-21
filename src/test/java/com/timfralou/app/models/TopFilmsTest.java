package com.timfralou.app.models;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import com.timfralou.app.BasicTest;
import com.timfralou.app.seeds.FilmSeed;

public class TopFilmsTest extends BasicTest {
    private TopFilms topFilms;

    @Test
    public void checksCtor() {
        Film[] films = new FilmSeed().films();
        try {
            for (Film film : films) {
                film.saveToDB(dbTEST.connect());
            }
            this.topFilms = new TopFilms(dbTEST.connect());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        assertTrue(topFilms.filmList().toString().contains("kinopoiskId"));
    }
}
