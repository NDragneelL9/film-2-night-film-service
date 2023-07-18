package com.timfralou.app.servlets;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.json.JSONArray;
import org.json.JSONObject;

import com.timfralou.app.models.Film;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet(urlPatterns = "/films/top-250")
public class TopFilmsServlet extends BaseServlet {

    public void doPut(HttpServletRequest servRequest, HttpServletResponse servResponse) {
        try {
            String responseJSON = "";
            String jsonFilmsPage = super.knpApi().getFilmsPage(1);
            JSONObject jsonObj = new JSONObject(jsonFilmsPage);
            if (jsonObj.has("message")) {
                responseJSON = super.objMapper().writeValueAsString(jsonObj.getString("message"));
            } else {
                int pagesCount = jsonObj.getInt("pagesCount");
                List<Film> topFilms = new ArrayList<Film>();
                for (int i = 1; i <= pagesCount; i++) {
                    String filmsPage = super.knpApi().getFilmsPage(i);
                    JSONObject filmJSON = new JSONObject(filmsPage);
                    JSONArray jsonFilms = filmJSON.getJSONArray("films");
                    Film[] films = super.objMapper().readValue(jsonFilms.toString(), Film[].class);
                    for (Film film : films) {
                        film.saveToDB(super.dbConn());
                    }
                    topFilms = Stream.concat(topFilms.stream(), Arrays.stream(films)).collect(Collectors.toList());

                    responseJSON = super.objMapper().writeValueAsString(topFilms);
                }
            }
            handleResponse(servResponse, responseJSON);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}