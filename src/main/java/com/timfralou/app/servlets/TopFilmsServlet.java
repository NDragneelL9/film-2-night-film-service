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
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        ObjectMapper objectMapper = new ObjectMapper();

        KinopoiskAPI knpApi = new KinopoiskAPI();
        String jsonPagesCount = knpApi.getFilmsPage(1);
        JSONObject jsonObj = new JSONObject(jsonPagesCount);
        int pagesCount = jsonObj.getInt("pagesCount");

        FilmList topFilms = new FilmList(new ArrayList<Film>());
        for (int i = 1; i <= pagesCount; i++) {
            List<Film> nextfilmList = getFilmsList(objectMapper, knpApi, i);
            topFilms = new FilmList(
                    Stream.concat(topFilms.filmList().stream(), nextfilmList.stream()).collect(Collectors.toList()));
        }

        try {
            topFilms.saveToDB(dbType.MAIN);
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        String responseJSON = objectMapper.writeValueAsString(topFilms.filmList());
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

    private void handleResponse(HttpServletResponse servResponse, String responseJSON) {
        try {
            servResponse.setCharacterEncoding("UTF-8");
            servResponse.setContentType("application/json");
            PrintWriter out = servResponse.getWriter();
            out.println(responseJSON);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}