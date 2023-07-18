package com.timfralou.app.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Genre {
    @JsonProperty("genre")
    private String genre;

    public Genre() {
        // need for Jackson
    }

    public Genre(String s) {
        this.genre = s;
    }

    /**
     * Saves genre to database
     * 
     * @param Connection conn - database connection
     * @return int insertCount - number of inserted rows
     */
    public int saveToDB(Connection conn) {
        try {
            PreparedStatement pstmt = conn
                    .prepareStatement("INSERT INTO genres (\"genre\") VALUES (?) ON CONFLICT (\"genre\") DO NOTHING;");
            pstmt.setString(1, genre);
            int insertCount = pstmt.executeUpdate();
            return insertCount;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    /**
     * Saves genre film relation to database
     * 
     * @param Connection conn - database connection
     * @param int        kinopoiskId - film id associated with this country
     * @return int insertCount - number of inserted rows
     */
    public int saveFilmRelationToDB(Connection conn, int kinopoiskId) {
        String INSERT_FILMS_GENRES = "INSERT INTO films_genres(film_id, genre_id)" +
                " SELECT f.film_id, g.genre_id FROM films as f, genres as g" +
                " WHERE f.\"kinopoiskId\" = ? AND g.\"genre\" = ?" +
                " ON CONFLICT (film_id, genre_id) DO NOTHING;";
        try {
            PreparedStatement pstmt = conn.prepareStatement(INSERT_FILMS_GENRES);
            pstmt.setInt(1, kinopoiskId);
            pstmt.setString(2, genre);
            int affectedrows = pstmt.executeUpdate();
            return affectedrows;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Genre [genre=" + genre + "]";
    }

}
