package com.timfralou.app.schedulers;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.IOException;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.timfralou.app.BasicTest;
import com.timfralou.app.models.TopFilms;

public class TopFilmsJobTest extends BasicTest {
    @Test
    public void checksJob() throws SQLException, IOException {
        TopFilmsJob tfb = new TopFilmsJob();
        tfb.run();
        TopFilms topFilms = new TopFilms(dbTEST.connect());
        String topFilmJson = new ObjectMapper().writeValueAsString(topFilms.pgFilmList());
        assertTrue(topFilmJson.toString().contains("kinopoiskId"));
        dbTEST.updateQuery("delete from films;");
    }
}
