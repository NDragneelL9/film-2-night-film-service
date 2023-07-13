package com.timfralou.app.api;

public class KinopoiskUrls {
    private final String BASE_URL;
    private final String TOP_250_URL;
    private final String FILTERS_URL;
    private final String FILM_URL;
    private final String PAGE_PARAM;

    public KinopoiskUrls() {
        this.BASE_URL = "https://kinopoiskapiunofficial.tech/";
        this.TOP_250_URL = "api/v2.2/films/top?type=TOP_250_BEST_FILMS";
        this.FILTERS_URL = "api/v2.2/films/filters";
        this.FILM_URL = "api/v2.2/films/";
        this.PAGE_PARAM = "&page=";
    }

    public String topFilmsURL() {
        return BASE_URL + TOP_250_URL + PAGE_PARAM;
    }

    public String filmFiltersURL() {
        return BASE_URL + FILTERS_URL;
    }

    public String filmURL() {
        return BASE_URL + FILM_URL;
    }
}
