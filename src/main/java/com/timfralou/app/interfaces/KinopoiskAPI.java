package com.timfralou.app.interfaces;

public interface KinopoiskAPI {
    public String getFilmsPage(int page);

    public String getFilmFilters();

    public String getFilm(String pathInfo);
}
