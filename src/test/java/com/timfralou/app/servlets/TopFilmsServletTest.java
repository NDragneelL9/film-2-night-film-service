package com.timfralou.app.servlets;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import com.timfralou.app.models.FilmList;
import com.timfralou.app.postgresql.PostgreDB;
import com.timfralou.app.seeds.FilmListSeed;

public class TopFilmsServletTest {

    @Test
    public void defaultAssert() {
        String text = "text";
        assertEquals("text", text);
    }

    @Test
    public void saveFilmsToDB() throws SQLException {
        String DB_TYPE = "TEST";
        FilmList filmList = new FilmList(new FilmListSeed().filmList());
        assertEquals("Succesfully saved to " + DB_TYPE, filmList.saveToDB(DB_TYPE));
    }

    @AfterAll
    public static void cleanUp() throws SQLException {
        PostgreDB db = new PostgreDB("TEST");
        db.updateQuery("DELETE FROM films");
    }
}
