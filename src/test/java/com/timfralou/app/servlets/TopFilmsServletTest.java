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
            dbTEST.updateQuery("DELETE from films");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}