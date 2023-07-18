package com.timfralou.app.servlets;

import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONObject;
import com.timfralou.app.models.Genre;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/genres")
public class GenresServlet extends BaseServlet {

    public void doPut(HttpServletRequest servRequest, HttpServletResponse servResponse) {
        try {
            String responseJSON = "";
            String jsonFilters = super.knpApi().getFilmFilters();
            JSONObject jsonObj = new JSONObject(jsonFilters);
            if (jsonObj.has("message")) {
                responseJSON = super.objMapper().writeValueAsString(jsonObj.getString("message"));
            } else {
                JSONArray jsonArrGenres = jsonObj.getJSONArray("genres");
                Genre[] genres = super.objMapper().readValue(jsonArrGenres.toString(), Genre[].class);
                for (Genre genre : genres) {
                    genre.saveToDB(super.dbConn());
                }
                responseJSON = super.objMapper().writeValueAsString(genres);
            }
            handleResponse(servResponse, responseJSON);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
