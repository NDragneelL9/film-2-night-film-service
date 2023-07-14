package com.timfralou.app.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timfralou.app.api.KinopoiskAPI;
import com.timfralou.app.postgresql.PostgreDB;
import com.timfralou.app.postgresql.dbType;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvEntry;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet
public class BaseServlet extends HttpServlet {
    private Dotenv dotenv;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private KinopoiskAPI knpApi;;
    private Connection dbConn;
    public static final int BATCH_SIZE = 20;

    public void init() {
        try {
            Class.forName("org.postgresql.Driver");
            Dotenv dockerEnv = Dotenv.configure()
                    .directory("/usr/local/")
                    .filename("env")
                    .ignoreIfMissing()
                    .load();
            Dotenv testEnv = Dotenv.configure()
                    .filename(".env.test")
                    .load();
            PostgreDB db;
            if (!dockerEnv.get("KNPSK_API_KEY", "").isEmpty()) {
                this.dotenv = dockerEnv;
                db = new PostgreDB(dbType.MAIN, dotenv);
            } else {
                this.dotenv = testEnv;
                db = new PostgreDB(dbType.TEST, dotenv);
            }
            this.knpApi = new KinopoiskAPI(dotenv);
            this.dbConn = db.connect();
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }

    public Connection dbConn() {
        return dbConn;
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
            out.write(responseJSON);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}