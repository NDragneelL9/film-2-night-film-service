package com.timfralou.app.servlets;

import java.io.*;
import com.timfralou.app.models.Film;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet(urlPatterns = "/films/*")
public class FilmServlet extends BaseServlet {

    public void doPut(HttpServletRequest servRequest, HttpServletResponse servResponse)
            throws IOException, ServletException {

        String pathInfo = servRequest.getPathInfo();
        String filmJson = super.knpApi().getFilm(pathInfo);
        Film film = super.objMapper().readValue(filmJson, Film.class);
        film.updateInDB(super.postgres().conn());
        String responseJSON = super.objMapper().writeValueAsString(film);
        handleResponse(servResponse, responseJSON);
    }
}
