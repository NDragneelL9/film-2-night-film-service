package com.timfralou.app.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timfralou.app.api.KinopoiskAPI;
import com.timfralou.app.postgresql.PostgreDB;
import com.timfralou.app.postgresql.dbType;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet
public class BaseServlet extends HttpServlet {
    private final Dotenv dotenv = Dotenv.configure()
            .directory("/usr/local/")
            .filename("env")
            .load();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final KinopoiskAPI knpApi = new KinopoiskAPI();
    private PostgreDB db;
    public static final int BATCH_SIZE = 20;

    public void init() throws ServletException {
        try {
            Class.forName("org.postgresql.Driver");
            this.db = new PostgreDB(dbType.MAIN);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public PostgreDB postgres() {
        return db;
    }

    public KinopoiskAPI knpApi() {
        return knpApi;
    }

    public ObjectMapper objMapper() {
        return objectMapper;
    }

    public Dotenv dotenv() {
        return dotenv;
    }

    public void handleResponse(HttpServletResponse servResponse, String responseJSON) {
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