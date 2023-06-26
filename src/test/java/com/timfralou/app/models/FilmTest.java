package com.timfralou.app.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timfralou.app.seeds.FilmSeed;

public class FilmTest {
    private JSONObject filmJson;

    public FilmTest() {
        String jsonTxt = new String();
        try {
            jsonTxt = new String(
                    Files.readAllBytes(Paths.get("src/test/java/com/timfralou/app/seeds/jsons/Film.json")));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        JSONObject jsonObj = new JSONObject(jsonTxt);
        this.filmJson = jsonObj;
    }

    @Test
    public void hasKinopoiskId() {
        int actualId = filmJson.optInt("kinopoiskId", -1);
        Film film = new FilmSeed().film();
        assertEquals(film.kinopoiskId(), actualId);
    }

    @Test
    public void hasImdbId() {
        String actualId = filmJson.optString("imdbId", "");
        Film film = new FilmSeed().film();
        assertEquals(film.imdbId(), actualId);
    }

    @Test
    public void hasNameRu() {
        String actualNameRu = filmJson.optString("nameRu", "");
        Film film = new FilmSeed().film();
        assertEquals(film.nameRu(), actualNameRu);
    }

    @Test
    public void hasNameEn() {
        String actualNameEn = filmJson.optString("nameEn", "");
        Film film = new FilmSeed().film();
        assertEquals(film.nameEn(), actualNameEn);
    }

    @Test
    public void hasNameOriginal() {
        String actualNameOrig = filmJson.optString("nameOriginal", "");
        Film film = new FilmSeed().film();
        assertEquals(film.nameOriginal(), actualNameOrig);
    }

    @Test
    public void hasReviewsCount() {
        int actualReviewsCount = filmJson.optInt("reviewsCount", -1);
        Film film = new FilmSeed().film();
        assertEquals(film.reviewsCount(), actualReviewsCount);
    }

    @Test
    public void hasRatingKinopoisk() {
        String actualRating = Objects.requireNonNullElse(String.valueOf(filmJson.getDouble("ratingKinopoisk")), "");
        Film film = new FilmSeed().film();
        assertEquals(film.ratingKinopoisk(), actualRating);
    }

    @Test
    public void hasRatingKinopoiskVoteCount() {
        int actualVoteCount = filmJson.optInt("ratingKinopoiskVoteCount", -1);
        Film film = new FilmSeed().film();
        assertEquals(film.ratingKinopoiskVoteCount(), actualVoteCount);
    }

    @Test
    public void hasRatingImdb() {
        String actualRating = Objects.requireNonNullElse(String.valueOf(filmJson.getDouble("ratingImdb")), "");
        Film film = new FilmSeed().film();
        assertEquals(film.ratingImdb(), actualRating);
    }

    @Test
    public void hasRatingImdbVoteCount() {
        int actualVoteCount = filmJson.optInt("ratingImdbVoteCount", -1);
        Film film = new FilmSeed().film();
        assertEquals(film.ratingImdbVoteCount(), actualVoteCount);
    }

    @Test
    public void hasWebUrl() {
        String actualWebUrl = filmJson.optString("webUrl", "");
        Film film = new FilmSeed().film();
        assertEquals(film.webUrl(), actualWebUrl);
    }

    @Test
    public void hasYear() {
        int actualYear = filmJson.optInt("year", -1);
        Film film = new FilmSeed().film();
        assertEquals(film.year(), actualYear);
    }

    @Test
    public void hasFilmLength() {
        int actualLength = filmJson.optInt("filmLength", -1);
        Film film = new FilmSeed().film();
        assertEquals(film.filmLength(), actualLength);
    }

    @Test
    public void hasDescription() {
        String actualDesc = filmJson.optString("description", "");
        Film film = new FilmSeed().film();
        assertEquals(film.description(), actualDesc);
    }

    @Test
    public void hasType() {
        filmType actualType = filmType.valueOf(filmJson.optString("type", "UNKNOWN"));
        Film film = new FilmSeed().film();
        assertEquals(film.type(), actualType);
    }

    @Test
    public void hasRatingMpaa() {
        String actualRatingMpaa = filmJson.optString("ratingMpaa", "");
        Film film = new FilmSeed().film();
        assertEquals(film.ratingMpaa(), actualRatingMpaa);
    }

    @Test
    public void hasAgeLimits() {
        String actualAgeLimits = filmJson.optString("ratingAgeLimits", "");
        Film film = new FilmSeed().film();
        assertEquals(film.ratingAgeLimits(), actualAgeLimits);
    }

    @Test
    public void hasImax() {
        Boolean actuallyHasImax = filmJson.optBoolean("hasImax", false);
        Film film = new FilmSeed().film();
        assertEquals(film.hasImax(), actuallyHasImax);
    }

    @Test
    public void has3D() {
        Boolean actuallyHas3D = filmJson.optBoolean("has3D", false);
        Film film = new FilmSeed().film();
        assertEquals(film.has3D(), actuallyHas3D);
    }

    @Test
    public void hasLastSync() {
        String actualLastSync = filmJson.optString("lastSync", "");
        Film film = new FilmSeed().film();
        assertEquals(film.lastSync(), actualLastSync);
    }

    @Test
    public void hasGenres() {
        JSONArray arrayJson = filmJson.optJSONArray("genres");
        var genres = Objects.requireNonNullElse(arrayJson, new Genre[] {});
        ObjectMapper objectMapper = new ObjectMapper();
        Genre[] actualGenres = new Genre[] {};
        try {
            actualGenres = objectMapper.readValue(genres.toString(), Genre[].class);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Film film = new FilmSeed().film();
        assertEquals(Arrays.toString(film.genres()), Arrays.toString(actualGenres));
    }

    @Test
    public void hasCountries() {
        JSONArray arrayJson = filmJson.getJSONArray("countries");
        var countries = Objects.requireNonNullElse(arrayJson, new Country[] {});
        ObjectMapper objectMapper = new ObjectMapper();
        Country[] actualCountries = new Country[] {};
        try {
            actualCountries = objectMapper.readValue(countries.toString(), Country[].class);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Film film = new FilmSeed().film();
        assertEquals(Arrays.toString(film.countries()), Arrays.toString(actualCountries));
    }
}
