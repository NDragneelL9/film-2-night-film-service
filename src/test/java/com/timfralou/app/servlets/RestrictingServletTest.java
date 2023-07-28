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
import com.timfralou.app.seeds.FilmSeed;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RestrictingServletTest extends BasicTest {
    @Test
    public void checksDoPutMethod() throws SQLException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RestrictingServlet restrictingServlet = new RestrictingServlet();
        new FilmSeed().film().saveToDB(dbTEST.connect());
        restrictingServlet.init();
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        String filmIdURL = "/448";
        when(request.getPathInfo()).thenReturn(filmIdURL);
        when(response.getWriter()).thenReturn(writer);
        restrictingServlet.doPut(request, response);
        assertTrue(stringWriter.toString().contains("is now banned"));
        dbTEST.updateQuery("delete from films");
    }
}