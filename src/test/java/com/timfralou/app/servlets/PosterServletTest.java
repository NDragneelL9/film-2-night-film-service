package com.timfralou.app.servlets;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import com.timfralou.app.BasicTest;
import com.timfralou.app.models.Film;
import com.timfralou.app.models.TopFilms;
import com.timfralou.app.seeds.FilmSeed;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class PosterServletTest extends BasicTest {
    @Test
    public void checksDoPutMethod() throws SQLException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        PosterServlet posterServlet = new PosterServlet();
        Film[] films = new FilmSeed().films();
        posterServlet.init();
        for (Film film : films) {
            film.saveToDB(dbTEST.connect());
        }
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);
        posterServlet.doPut(request, response);
        assertTrue(stringWriter.toString().contains("Posters successfully downloaded"));
        cleanUp();
    }

    private void cleanUp() throws SQLException {
        TopFilms topFilms = new TopFilms(dbTEST.connect());
        for (Film film : topFilms.pgFilmList()) {
            film.deletePoster();
        }
        dbTEST.updateQuery("DELETE from films");
    }

}
