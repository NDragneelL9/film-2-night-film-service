package com.timfralou.app.servlets;

import java.io.IOException;

import com.timfralou.app.models.FilmFilters;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/film-filters")
public class FilmFiltersServlet extends BaseServlet {
    public void doPut(HttpServletRequest servRequest, HttpServletResponse servResponse) {
        try {
            FilmFilters filmFilters = new FilmFilters(super.dbConn());
            String responseJSON = filmFilters.syncFilmFilters(super.knpApi());
            handleResponse(servResponse, responseJSON);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
