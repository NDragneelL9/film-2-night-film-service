package com.timfralou.app.servlets;

import com.timfralou.app.models.TopFilms;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/films/top-250/posters")
public class PosterServlet extends BaseServlet {
    public void doPut(HttpServletRequest servRequest, HttpServletResponse servResponse) {
        TopFilms topFilms = new TopFilms(dbConn());
        String responseJSON = topFilms.loadPosters();
        handleResponse(servResponse, responseJSON);
    }
}
