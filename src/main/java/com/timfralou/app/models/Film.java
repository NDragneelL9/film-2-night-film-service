package com.timfralou.app.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = Include.NON_EMPTY, content = Include.NON_NULL)
public class Film {
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
        // need for Jackson
    }

    /**
     * Converts film length string in HH:MM format to an interger number
     * 
     * @param String s - film length in HH:MM format
     * @return int length - integer number of minutes in the film
     */
    private int toMins(String s) {
        if (s == null) {
            return 0;
        }
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

    /**
     * Saves film to database
     * 
     * @param Connection conn - database connection
     * @return int insertCount - number of inserted rows
     */
    public int saveToDB(Connection conn) {
        try {
            PreparedStatement pstmt = conn
                    .prepareStatement(
                            "INSERT INTO films (\"kinopoiskId\", \"nameRu\", \"ratingKinopoisk\"," +
                                    "\"ratingKinopoiskVoteCount\", \"year\", \"filmLength\"," +
                                    "\"imdbId\", \"nameEn\", \"nameOriginal\", \"reviewsCount\"," +
                                    " \"ratingImdb\", \"ratingImdbVoteCount\", \"webUrl\", \"description\", \"type\"," +
                                    " \"ratingMpaa\", \"ratingAgeLimits\", \"hasImax\", \"has3D\", \"lastSync\")" +
                                    " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)" +
                                    " ON CONFLICT (\"kinopoiskId\") DO NOTHING;");
            pstmt.setInt(1, kinopoiskId);
            pstmt.setString(2, nameRu);
            pstmt.setString(3, ratingKinopoisk);
            pstmt.setInt(4, ratingKinopoiskVoteCount);
            pstmt.setInt(5, year);
            pstmt.setInt(6, toMins(filmLength));
            pstmt.setString(7, imdbId);
            pstmt.setString(8, nameEn);
            pstmt.setString(9, nameOriginal);
            pstmt.setInt(10, reviewsCount);
            pstmt.setString(11, ratingImdb);
            pstmt.setInt(12, ratingImdbVoteCount);
            pstmt.setString(13, webUrl);
            pstmt.setString(14, description);
            pstmt.setObject(15, type, java.sql.Types.OTHER);
            pstmt.setString(16, ratingMpaa);
            pstmt.setString(17, ratingAgeLimits);
            pstmt.setBoolean(18, hasImax);
            pstmt.setBoolean(19, has3D);
            pstmt.setString(20, lastSync);
            int insertCount = pstmt.executeUpdate();
            if (countries != null) {
                for (Country country : countries) {
                    country.saveFilmRelationToDB(conn, kinopoiskId);
                }
            }
            if (genres != null) {
                for (Genre genre : genres) {
                    genre.saveFilmRelationToDB(conn, kinopoiskId);
                }
            }
            return insertCount;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    /**
     * Saves genre to database
     * 
     * @param Connection conn - database connection
     * @return int affectedrows - number of updated rows
     */
    public int updateInDB(Connection conn) {
        try {
            PreparedStatement pstmt = conn.prepareStatement(
                    "UPDATE films SET \"nameRu\" = ?, \"ratingKinopoisk\" = ?, \"ratingKinopoiskVoteCount\" = ?," +
                            " \"year\" = ?, \"filmLength\" = ?, \"imdbId\" = ?, \"nameEn\" = ?," +
                            " \"nameOriginal\" = ?, \"reviewsCount\" = ?, \"ratingImdb\" = ?," +
                            " \"ratingImdbVoteCount\" = ?, \"webUrl\" = ?, \"description\" = ?," +
                            " \"type\" = ?,\"ratingMpaa\" = ?, \"ratingAgeLimits\" = ?, \"hasImax\" = ?," +
                            " \"has3D\" = ?, \"lastSync\" = ?" +
                            " WHERE \"kinopoiskId\" = ?;");
            pstmt.setString(1, nameRu);
            pstmt.setString(2, ratingKinopoisk);
            pstmt.setInt(3, ratingKinopoiskVoteCount);
            pstmt.setInt(4, year);
            pstmt.setInt(5, toMins(filmLength));
            pstmt.setString(6, imdbId);
            pstmt.setString(7, nameEn == null ? nameOriginal : nameEn);
            pstmt.setString(8, nameOriginal == null ? nameEn : nameOriginal);
            pstmt.setInt(9, reviewsCount);
            pstmt.setString(10, ratingImdb);
            pstmt.setInt(11, ratingImdbVoteCount);
            pstmt.setString(12, webUrl);
            pstmt.setString(13, description);
            pstmt.setObject(14, type, java.sql.Types.OTHER);
            pstmt.setString(15, ratingMpaa);
            pstmt.setString(16, ratingAgeLimits);
            pstmt.setBoolean(17, hasImax);
            pstmt.setBoolean(18, has3D);
            pstmt.setString(19, lastSync);
            pstmt.setInt(20, kinopoiskId);
            return pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Film [kinopoiskId=" + kinopoiskId + ", nameRu=" + nameRu + ", ratingKinopoisk=" + ratingKinopoisk
                + ", year=" + year + ", filmLength=" + toMins(filmLength) + ", genres=" + Arrays.toString(genres)
                + ", countries=" + Arrays.toString(countries) + "]";
    }
}
