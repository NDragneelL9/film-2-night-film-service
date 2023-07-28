package com.timfralou.app.servlets;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import com.timfralou.app.BasicTest;
import com.timfralou.app.models.Film;
import com.timfralou.app.models.TopFilms;
import com.timfralou.app.seeds.FilmSeed;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class TopFilmsServletTest extends BasicTest {

    @Test
    public void checksDoPutMethod() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        TopFilmsServlet topFilmsServlet = new TopFilmsServlet();
        try {
            topFilmsServlet.init();
            StringWriter stringWriter = new StringWriter();
            PrintWriter writer = new PrintWriter(stringWriter);
            when(response.getWriter()).thenReturn(writer);
            topFilmsServlet.doPut(request, response);
            assertTrue(stringWriter.toString().contains("kinopoiskId"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void checksDoGetMethod() throws SQLException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        TopFilmsServlet topFilmsServlet = new TopFilmsServlet();
        Film film = new FilmSeed().film();
        film.saveToDB(dbTEST.connect());
        topFilmsServlet.init();
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);
        topFilmsServlet.doGet(request, response);
        assertTrue(stringWriter.toString().contains("Forrest Gump"));
    }

    @AfterAll
    public static void cleanUp() throws SQLException {
        TopFilms topFilms = new TopFilms(dbTEST.connect());
        for (Film film : topFilms.pgFilmList()) {
            film.deletePoster();
        }
        dbTEST.updateQuery("DELETE from films");
    }
}
