package com.timfralou.app.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Country {
    @JsonProperty("country")
    private String country;

    public Country() {
        // need for Jackson
    }

    public Country(String s) {
        this.country = s;
    }

    /**
     * Saves country to database
     * 
     * @param Connection conn - database connection
     * @return int insertCount - number of inserted rows
     */
    public int saveToDB(Connection conn) {
        int insertCount = -1;
        try {
            PreparedStatement pstmt = conn.prepareStatement(
                    "INSERT INTO countries (\"country\") VALUES (?) ON CONFLICT (\"country\") DO NOTHING;");
            pstmt.setString(1, country);
            insertCount = pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return insertCount;
    }

    /**
     * Saves country film relation to database
     * 
     * @param Connection conn - database connection
     * @param int        kinopoiskId - film associated with this country
     * @return int insertCount - number of inserted rows
     */
    public int saveFilmRelationToDB(Connection conn, int kinopoiskId) {
        String INSERT_FILMS_COUNTRIES = "INSERT INTO films_countries(film_id, country_id)" +
                " SELECT f.film_id, c.country_id FROM films as f, countries as c" +
                " WHERE f.\"kinopoiskId\" = ? AND c.\"country\" = ?" +
                " ON CONFLICT (film_id, country_id) DO NOTHING;";
        int insertCount = -1;
        try {
            PreparedStatement pstmt = conn.prepareStatement(INSERT_FILMS_COUNTRIES);
            pstmt.setInt(1, kinopoiskId);
            pstmt.setString(2, country);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return insertCount;
    }

    @Override
    public String toString() {
        return "Country [country=" + country + "]";
    }

}
