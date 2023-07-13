package com.timfralou.app.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import com.timfralou.app.BasicTest;
import com.timfralou.app.seeds.GenreSeed;

public class GenreTest extends BasicTest {
    private Genre genre = new GenreSeed().genre();

    @Test
    public void constructsCountry() {
        Genre genre = new Genre("Incredible Genre");
        boolean isCorrectName = genre.toString().contains("Incredible Genre");
        assertEquals(true, isCorrectName);
    }

    @Test
    public void writesToString() {
        boolean printsToString = genre.toString().startsWith("Genre [genre=");
        assertEquals(true, printsToString);
    }

    @Test
    public void savesGenreToDB() throws SQLException {
        int insertedRows = genre.saveToDB(dbTEST.connect());
        assertEquals(1, insertedRows);
    }

    @AfterAll
    public static void cleanUp() throws SQLException {
        dbTEST.updateQuery("DELETE FROM genres");
    }
}
