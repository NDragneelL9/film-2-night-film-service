package com.timfralou.app.models;

import java.util.Arrays;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

enum filmType {
    FILM,
    VIDEO,
    TV_SERIES,
    MINI_SERIES,
    TV_SHOW,
    UNKNOWN
}

@JsonIgnoreProperties(ignoreUnknown = true)
public class Film {
    @JsonIgnore
    private long id;
    @JsonProperty("kinopoiskId")
    @JsonAlias("filmId")
    private int kinopoiskId;
    @JsonProperty("imdbId")
    private String imdbId;
    @JsonProperty("nameRu")
    private String nameRu;
    @JsonProperty("nameEn")
    private String nameEn;
    @JsonProperty("nameOriginal")
    private String nameOriginal;
    @JsonProperty("reviewsCount")
    private int reviewsCount;
    @JsonProperty("ratingKinopoisk")
    @JsonAlias("rating")
    private String ratingKinopoisk;
    @JsonProperty("ratingKinopoiskVoteCount")
    @JsonAlias("ratingVoteCount")
    private int ratingKinopoiskVoteCount;
    @JsonProperty("ratingImdb")
    private String ratingImdb;
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
    @JsonProperty("type")
    private filmType type;
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
    @JsonProperty("genres")
    private Genre[] genres;
    @JsonProperty("countries")
    private Country[] countries;

    public Film() {
        // keep
    }

    public long id() {
        return id;
    }

    public int kinopoiskId() {
        return Optional.ofNullable(kinopoiskId).orElse(-1);
    }

    public String imdbId() {
        return Optional.ofNullable(imdbId).orElse("");
    }

    public String nameRu() {
        return Optional.ofNullable(nameRu).orElse("");
    }

    public String nameEn() {
        return Optional.ofNullable(nameEn).orElse("");
    }

    public String nameOriginal() {
        return Optional.ofNullable(nameOriginal).orElse("");
    }

    public int reviewsCount() {
        return Optional.ofNullable(reviewsCount).orElse(-1);
    }

    public String ratingKinopoisk() {
        return Optional.ofNullable(ratingKinopoisk).orElse("");
    }

    public int ratingKinopoiskVoteCount() {
        return Optional.ofNullable(ratingKinopoiskVoteCount).orElse(-1);
    }

    public String ratingImdb() {
        return Optional.ofNullable(ratingImdb).orElse("");
    }

    public int ratingImdbVoteCount() {
        return Optional.ofNullable(ratingImdbVoteCount).orElse(-1);
    }

    public String webUrl() {
        return Optional.ofNullable(webUrl).orElse("");
    }

    public int year() {
        return Optional.ofNullable(year).orElse(-1);
    }

    public int filmLength() {
        return Optional.ofNullable(toMins(filmLength)).orElse(-1);
    }

    public String description() {
        return Optional.ofNullable(description).orElse("");
    }

    public filmType type() {
        return Optional.ofNullable(type).orElse(filmType.UNKNOWN);
    }

    public String ratingMpaa() {
        return Optional.ofNullable(ratingMpaa).orElse("");
    }

    public String ratingAgeLimits() {
        return Optional.ofNullable(ratingAgeLimits).orElse("");
    }

    public boolean hasImax() {
        return Optional.ofNullable(hasImax).orElse(false);
    }

    public boolean has3D() {
        return Optional.ofNullable(has3D).orElse(false);
    }

    public String lastSync() {
        return Optional.ofNullable(lastSync).orElse("");
    }

    public Genre[] genres() {
        return Optional.ofNullable(genres).orElse(new Genre[] {});
    }

    public Country[] countries() {
        return Optional.ofNullable(countries).orElse(new Country[] {});
    }

    private static int toMins(String s) {
        if (s.matches("^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$")) {
            String[] hourMin = s.split(":");
            int hour = Integer.parseInt(hourMin[0]);
            int mins = Integer.parseInt(hourMin[1]);
            int hoursInMins = hour * 60;
            return hoursInMins + mins;
        } else {
            int length = Integer.valueOf(s);
            return length;
        }
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
