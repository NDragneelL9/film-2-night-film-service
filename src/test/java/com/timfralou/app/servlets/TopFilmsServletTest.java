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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timfralou.app.BasicTest;
import com.timfralou.app.models.Film;
import com.timfralou.app.models.TopFilms;

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

    @AfterAll
    public static void cleanUp() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            TopFilms topFilms = new TopFilms(dbTEST.connect());
            String topFilmJson = topFilms.filmList();
            Film[] films = objectMapper.readValue(topFilmJson.toString(), Film[].class);
            for (Film film : films) {
                film.deletePoster();
            }
        } catch (SQLException | IOException ex) {
            ex.printStackTrace();
        }
        dbTEST.updateQuery("DELETE from films");
    }
}
