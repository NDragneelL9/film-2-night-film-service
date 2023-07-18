package com.timfralou.app.api;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.timfralou.app.BasicTest;

public class KinopoiskAPITest extends BasicTest {
    private final KinopoiskAPI knpApi = new KinopoiskAPI(localEnv);

    @Test
    public void checksCtor() {
        KinopoiskAPI api = new KinopoiskAPI(localEnv);
        assertTrue(api.getClass().toString().contains("KinopoiskAPI"));
    }

    @Test
    public void checksGetFilmsPage() {
        assertTrue(knpApi.getFilmsPage(1).toString().contains("films"));
    }

    @Test
    public void checksGetFilmFilters() {
        assertTrue(knpApi.getFilmFilters().toString().contains("genres"));
    }

    @Test
    public void checksGetFilm() {
        assertTrue(knpApi.getFilm("/435").toString().contains("435"));
    }
}
