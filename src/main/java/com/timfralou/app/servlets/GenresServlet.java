package com.timfralou.app.servlets;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.timfralou.app.models.Genre;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/genres")
public class GenresServlet extends BaseServlet {

    public void doPut(HttpServletRequest servRequest, HttpServletResponse servResponse)
            throws IOException, ServletException {

        String jsonFilters = super.knpApi().getFilmFilters();
        JSONArray jsonArrGenres = new JSONObject(jsonFilters).getJSONArray("genres");
        Genre[] genres = super.objMapper().readValue(jsonArrGenres.toString(), Genre[].class);
        for (Genre genre : genres) {
            genre.saveToDB(super.postgres().conn());
        }
        String responseJSON = super.objMapper().writeValueAsString(genres);
        handleResponse(servResponse, responseJSON);
    }
}
