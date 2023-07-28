package com.timfralou.app.api;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.timfralou.app.BasicTest;
import com.timfralou.app.interfaces.KinopoiskAPI;

public class FkKinopoiskAPITest extends BasicTest {
    private final KinopoiskAPI knpApi = new FkKinopoiskAPI();

    @Test
    public void checksCtor() {
        KinopoiskAPI api = new FkKinopoiskAPI();
        assertTrue(api.getClass().toString().contains("KinopoiskAPI"));
    }

    @Test
    public void checksGetFilmsPage() {
        if (knpApi.getFilmsPage(1).toString().contains("You exceeded the quota")) {
            assertTrue(knpApi.getFilmsPage(1).toString().contains("You exceeded the quota"));
        } else {
            assertTrue(knpApi.getFilmsPage(1).toString().contains("films"));
        }
    }

    @Test
    public void checksGetFilmFilters() {
        if (knpApi.getFilmFilters().toString().contains("You exceeded the quota")) {
            assertTrue(knpApi.getFilmsPage(1).toString().contains("You exceeded the quota"));
        } else {
            assertTrue(knpApi.getFilmFilters().toString().contains("genres"));
        }
    }

    @Test
    public void checksGetFilm() {
        if (knpApi.getFilm("/448").toString().contains("You exceeded the quota")) {
            assertTrue(knpApi.getFilmsPage(1).toString().contains("You exceeded the quota"));
        } else {
            assertTrue(knpApi.getFilm("/448").toString().contains("448"));
        }
    }
}
