package com.timfralou.models;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

enum filmType {
    FILM,
    VIDEO,
    TV_SERIES,
    MINI_SERIES,
    TV_SHOW
}

public class Film {
    @JsonIgnore
    private long id;
    @JsonProperty("kinopoiskId")
    @JsonAlias("filmId")
    private int kinopoiskId;
    // @JsonProperty("imdbId")
    // private int imdbId;
    @JsonProperty("nameRu")
    private String nameRu;
    // @JsonProperty("nameEn")
    // private String nameEn;
    // @JsonProperty("nameOriginal")
    // private String nameOriginal;
    // @JsonProperty("reviewsCount")
    // private int reviewsCount;
    @JsonProperty("ratingKinopoisk")
    @JsonAlias("rating")
    private String ratingKinopoisk;
    @JsonProperty("ratingKinopoiskVoteCount")
    @JsonAlias("ratingVoteCount")
    private int ratingKinopoiskVoteCount;
    // @JsonProperty("ratingImdb")
    // private String ratingImdb;
    // @JsonProperty("ratingImdbVoteCount")
    // private int ratingImdbVoteCount;
    // @JsonProperty("webUrl")
    // private String webUrl;
    @JsonProperty("year")
    private int year;
    @JsonProperty("filmLength")
    private String filmLength;
    // @JsonProperty("description")
    // private String description;
    // @JsonProperty("type")
    // private filmType type;
    // @JsonProperty("ratingMpaa")
    // private String ratingMpaa;
    // @JsonProperty("ratingAgeLimits")
    // private String ratingAgeLimits;
    // @JsonProperty("hasImax")
    // private boolean hasImax;
    // @JsonProperty("has3D")
    // private boolean has3D;
    // @JsonProperty("lastSync")
    // private String lastSync;
    @JsonProperty("genres")
    private Genre[] genres;
    @JsonProperty("countries")
    private Country[] countries;

    public Film() {
        // keep
    }

    public int kinopoiskId() {
        return kinopoiskId;
    }

    public String nameRu() {
        return nameRu;
    }

    public String ratingKinopoisk() {
        return ratingKinopoisk;
    }

    public int ratingKinopoiskVoteCount() {
        return ratingKinopoiskVoteCount;
    }

    public int year() {
        return year;
    }

    public int filmLength() {
        return toMins(filmLength);
    }

    public Genre[] genres() {
        return genres;
    }

    public Country[] countries() {
        return countries;
    }

    /**
     * @param s H:m timestamp, i.e. [Hour in day (0-23)]:[Minute in hour (0-59)]
     * @return total minutes after 00:00
     */
    private static int toMins(String s) {
        String[] hourMin = s.split(":");
        int hour = Integer.parseInt(hourMin[0]);
        int mins = Integer.parseInt(hourMin[1]);
        int hoursInMins = hour * 60;
        return hoursInMins + mins;
    }

    @Override
    public int hashCode() {
        return nameRu.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return nameRu.equals(((Film) obj).nameRu);
    }

    @Override
    public String toString() {
        return "Film [kinopoiskId=" + kinopoiskId + ", nameRu=" + nameRu + ", ratingKinopoisk=" + ratingKinopoisk
                + ", year=" + year + ", filmLength=" + filmLength + ", genres=" + Arrays.toString(genres)
                + ", countries=" + Arrays.toString(countries) + "]";
    }
}
