package com.timfralou.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

enum filmType {
    FILM,
    VIDEO,
    TV_SERIES,
    MINI_SERIES,
    TV_SHOW
}

public class Film {
    private long id;
    @JsonProperty("kinopoiskId")
    @JsonAlias("filmId")
    private int kinopoiskId;
    @JsonProperty("imdbId")
    private int imdbId;
    @JsonProperty("nameRu")
    private String nameRu;
    @JsonProperty("nameEn")
    private String nameEn;
    @JsonProperty("nameOriginal")
    private String nameOriginal;
    @JsonProperty("reviewsCount")
    private int reviewsCount;
    @JsonProperty("ratingKinopoisk")
    private float ratingKinopoisk;
    @JsonProperty("ratingKinopoiskVoteCount")
    private int ratingKinopoiskVoteCount;
    @JsonProperty("ratingImdb")
    private float ratingImdb;
    @JsonProperty("ratingImdbVoteCount")
    private int ratingImdbVoteCount;
    @JsonProperty("webUrl")
    private String webUrl;
    @JsonProperty("year")
    private int year;
    @JsonProperty("filmLength")
    private String filmLength;
    @JsonProperty("description")
    private String description;
    // private filmType type;
    @JsonProperty("ratingMpaa")
    private String ratingMpaa;
    @JsonProperty("ratingAgeLimits")
    private String ratingAgeLimits;
    @JsonProperty("hasImax")
    private boolean hasImax;
    @JsonProperty("has3D")
    private boolean has3D;
    @JsonProperty("lastSync")
    private String lastSync;

    public Film() {
        // keep
    }

    @Override
    public String toString() {
        return "Film [nameRu=" + nameRu + ", reviewsCount=" + reviewsCount + ", webUrl=" + webUrl + ", year=" + year
                + ", filmLength=" + filmLength + ", ratingMpaa=" + ratingMpaa + "]";
    }

}
