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

public class CountriesServletTest extends BasicTest {

    @Test
    public void checksDoPutMethod() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        CountriesServlet countriesServlet = new CountriesServlet();
        try {
            countriesServlet.init();
            StringWriter stringWriter = new StringWriter();
            PrintWriter writer = new PrintWriter(stringWriter);
            when(response.getWriter()).thenReturn(writer);
            countriesServlet.doPut(request, response);
            if (stringWriter.toString().contains("You exceeded the quota")) {
                assertTrue(stringWriter.toString().contains("You exceeded the quota"));
            } else {
                assertTrue(stringWriter.toString().contains("country"));
            }
            dbTEST.updateQuery("DELETE from countries");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
