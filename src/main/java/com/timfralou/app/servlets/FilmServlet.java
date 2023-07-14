package com.timfralou.app.servlets;

import java.io.*;

import org.json.JSONObject;

import com.timfralou.app.models.Film;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet(urlPatterns = "/films/*")
public class FilmServlet extends BaseServlet {

    public void doPut(HttpServletRequest servRequest, HttpServletResponse servResponse) {
        try {
            String responseJSON = "";
            String pathInfo = servRequest.getPathInfo();
            String filmJson = super.knpApi().getFilm(pathInfo);
            JSONObject jsonObj = new JSONObject(filmJson);
            if (jsonObj.has("message")) {
                responseJSON = super.objMapper().writeValueAsString(jsonObj.getString("message"));
            } else {
                Film film = super.objMapper().readValue(filmJson, Film.class);
                film.updateInDB(super.dbConn());
                responseJSON = super.objMapper().writeValueAsString(film);
            }
            handleResponse(servResponse, responseJSON);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
