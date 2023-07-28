package com.timfralou.app.schedulers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timfralou.app.BasicTest;
import com.timfralou.app.api.FkKinopoiskAPI;
import com.timfralou.app.models.FilmFilters;

public class FilmFiltersJobTest extends BasicTest {
    @Test
    public void checksJob() {
        new FilmFiltersJob().run();
        try {
            FilmFilters filmFilters = new FilmFilters(dbTEST.connect());
            String filmFiltersJson = new ObjectMapper()
                    .writeValueAsString(filmFilters.syncFilmFilters(new FkKinopoiskAPI()));
            ResultSet rs = dbTEST.selectQuery("Select * from genres;");
            while (rs.next()) {
                assertEquals("боевик", rs.getString("genre"));
            }
            assertTrue(filmFiltersJson.toString().contains("США"));
        } catch (SQLException | IOException ex) {
            ex.printStackTrace();
        }
    }

    @AfterAll
    public static void cleanUp() throws SQLException {
        dbTEST.updateQuery("DELETE FROM countries;");
        dbTEST.updateQuery("DELETE FROM genres;");
    }
}
