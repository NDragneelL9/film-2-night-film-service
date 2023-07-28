package com.timfralou.app.servlets;

import org.json.JSONObject;
import com.timfralou.app.models.Film;
import com.timfralou.app.models.PgFilm;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/films/change-permit/*")
public class RestrictingServlet extends BaseServlet {
    public void doPut(HttpServletRequest servRequest, HttpServletResponse servResponse) {
        String pathInfo = servRequest.getPathInfo();
        String[] pathParts = pathInfo.split("/");
        String filmId = pathParts[1];
        Film film = new PgFilm(super.dbConn()).pgFilmById(Integer.valueOf(filmId));
        JSONObject responseJSON = film.changePermit(super.dbConn());
        handleResponse(servResponse, responseJSON.toString());
    }
}
