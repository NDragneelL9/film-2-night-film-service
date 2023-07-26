package com.timfralou.app.servlets;

import java.io.IOException;
import com.timfralou.app.models.TopFilms;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet(urlPatterns = "/films/top-250")
public class TopFilmsServlet extends BaseServlet {

    public void doGet(HttpServletRequest servRequest, HttpServletResponse servResponse) {
        TopFilms topFilms = new TopFilms(dbConn());
        String responseJSON = "";
        try {
            responseJSON = super.objMapper().writeValueAsString(topFilms.pgFilmList());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        handleResponse(servResponse, responseJSON);
    }

    public void doPut(HttpServletRequest servRequest, HttpServletResponse servResponse) {
        try {
            TopFilms topFilms = new TopFilms(dbConn());
            String responseJSON = topFilms.syncTopFilms(super.knpApi());
            handleResponse(servResponse, responseJSON);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}