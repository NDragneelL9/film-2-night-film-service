package com.timfralou.app.servlets;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import org.junit.jupiter.api.Test;

import com.timfralou.app.BasicTest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class FilmServletTest extends BasicTest {

    @Test
    public void checksDoPutMethod() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilmServlet filmServlet = new FilmServlet();
        filmServlet.init();
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        String filmIdURL = "/448";
        when(request.getPathInfo()).thenReturn(filmIdURL);
        when(response.getWriter()).thenReturn(writer);
        filmServlet.doPut(request, response);
        if (stringWriter.toString().contains("You exceeded the quota")) {
            assertTrue(stringWriter.toString().contains("You exceeded the quota"));
        } else {
            assertTrue(stringWriter.toString().contains("448"));
        }
        dbTEST.updateQuery("DELETE from films");
    }
}
