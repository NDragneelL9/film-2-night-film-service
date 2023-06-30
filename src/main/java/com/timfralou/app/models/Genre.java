package com.timfralou.app.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Genre {
    private long id;
    @JsonProperty("genre")
    private String genre;

    public Genre() {
        // keep
    }

    public Genre(String s) {
        this.genre = s;
    }

    public long id() {
        return id;
    }

    public String genre() {
        return genre;
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
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO genres (\"genre\") VALUES (?)");
            pstmt.setString(1, genre);
            insertCount = pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return insertCount;
    }

    @Override
    public String toString() {
        return "Genre [genre=" + genre + "]";
    }
}
