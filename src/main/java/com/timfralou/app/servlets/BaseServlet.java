package com.timfralou.app.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.timfralou.app.api.FkKinopoiskAPI;
import com.timfralou.app.api.knpAPI;
import com.timfralou.app.interfaces.KinopoiskAPI;
import com.timfralou.app.postgresql.PostgreDB;
import com.timfralou.app.postgresql.dbType;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet
public class BaseServlet extends HttpServlet {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private KinopoiskAPI knpApi;;
    private Connection dbConn;

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
                    .ignoreIfMissing()
                    .load();
            Dotenv dotenv;
            PostgreDB db;
            if (!dockerEnv.get("KNPSK_API_KEY", "").isEmpty()) {
                db = new PostgreDB(dbType.MAIN, dockerEnv);
                dotenv = dockerEnv;
                this.knpApi = new knpAPI(dotenv);
            } else {
                db = new PostgreDB(dbType.TEST, testEnv);
                dotenv = testEnv;
                this.knpApi = new FkKinopoiskAPI();
            }
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