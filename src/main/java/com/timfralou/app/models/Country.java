package com.timfralou.app.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Country {
    @JsonIgnore
    private long id;
    @JsonProperty("country")
    private String country;

    public Country() {
        // keep
    }

    public Country(String s) {
        this.country = s;
    }

    public long id() {
        return id;
    }

    public String country() {
        return country;
    }

    /**
     * Saves genre to database
     * 
     * @param Connection conn - database connection
     * @return int insertCount - number of inserted rows
     */
    public int saveToDB(Connection conn) {
        int insertCount = -1;
        try {
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO countries (\"country\") VALUES (?)");
            pstmt.setString(1, country);
            insertCount = pstmt.executeUpdate();
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
