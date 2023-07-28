package com.timfralou.app.api;

import com.timfralou.app.interfaces.KinopoiskAPI;
import com.timfralou.app.seeds.FilmsPageSeed;
import com.timfralou.app.seeds.FilmPageSeed;
import com.timfralou.app.seeds.FiltersPage;

public class FkKinopoiskAPI implements KinopoiskAPI {

    public String getFilmsPage(int page) {
        return new FilmsPageSeed().filmPage();
    }

    public String getFilmFilters() {
        return new FiltersPage().filtersPage();
    }

    public String getFilm(String filmId) {
        return new FilmPageSeed().filmPage();
    }
}
