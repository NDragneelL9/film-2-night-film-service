package com.timfralou.app.servlets;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import com.timfralou.app.models.FilmList;
import com.timfralou.app.models.FkFilmsList;
import com.timfralou.app.postgresql.PostgreDB;

public class TopFilmsServletTest {

    @Test
    public void defaultAssert() {
        String text = "text";
        assertEquals("text", text);
    }

    @Test
    public void saveFilmsToDB() throws SQLException {
        String DB_TYPE = "TEST";
        FilmList filmList = new FkFilmsList().fakeList();
        assertEquals("Succesfully saved to " + DB_TYPE, filmList.saveToDB(DB_TYPE));
    }

    @AfterAll
    public static void cleanUp() throws SQLException {
        PostgreDB db = new PostgreDB("TEST");
        db.updateQuery("DELETE FROM films");
    }
}
