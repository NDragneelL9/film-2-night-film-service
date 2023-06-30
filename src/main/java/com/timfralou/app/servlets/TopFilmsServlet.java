package com.timfralou.app.servlets;

import com.timfralou.app.api.KinopoiskAPI;
import com.timfralou.app.postgresql.dbType;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.timfralou.app.models.Film;
import com.timfralou.app.models.FilmList;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet(urlPatterns = "/films/top-250")
public class TopFilmsServlet extends BaseServlet {

    public void doGet(HttpServletRequest servRequest, HttpServletResponse servResponse)
            throws IOException, ServletException {

        String jsonPagesCount = super.knpApi().getFilmsPage(1);
        JSONObject jsonObj = new JSONObject(jsonPagesCount);
        int pagesCount = jsonObj.getInt("pagesCount");

        FilmList topFilms = new FilmList(new ArrayList<Film>());
        for (int i = 1; i <= pagesCount; i++) {
            List<Film> nextfilmList = getFilmsList(super.objMapper(), super.knpApi(), i);
            topFilms = new FilmList(
                    Stream.concat(topFilms.filmList().stream(), nextfilmList.stream()).collect(Collectors.toList()));
        }

        try {
            topFilms.saveToDB(dbType.MAIN);
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        String responseJSON = super.objMapper().writeValueAsString(topFilms.filmList());
        handleResponse(servResponse, responseJSON);
    }

    private List<Film> getFilmsList(ObjectMapper objectMapper, KinopoiskAPI knpApi, int i) {
        try {
            String filmsPage = knpApi.getFilmsPage(i);
            JSONObject jsonObj = new JSONObject(filmsPage);
            JSONArray jsonFilms = jsonObj.getJSONArray("films");
            List<Film> nextfilmList = objectMapper.readValue(jsonFilms.toString(),
                    new TypeReference<ArrayList<Film>>() {
                    });
            return nextfilmList;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return new ArrayList<Film>();
    }
}