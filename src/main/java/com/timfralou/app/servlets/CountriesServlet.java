package com.timfralou.app.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.timfralou.app.api.KinopoiskAPI;
import com.timfralou.app.models.Country;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/countries")
public class CountriesServlet extends BaseServlet {
    private static final int BATCH_SIZE = 50;

    public void doGet(HttpServletRequest servRequest, HttpServletResponse servResponse)
            throws IOException, ServletException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        KinopoiskAPI knpApi = new KinopoiskAPI();
        ObjectMapper objectMapper = new ObjectMapper();

        String jsonFilters = knpApi.getFilmFilters();
        JSONObject jsonFiltersObj = new JSONObject(jsonFilters);
        JSONArray jsonCountries = jsonFiltersObj.getJSONArray("countries");
        List<Country> countriesList = objectMapper.readValue(jsonCountries.toString(),
                new TypeReference<ArrayList<Country>>() {
                });
        try {
            saveToDB(countriesList);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        servResponse.setCharacterEncoding("UTF-8");
        servResponse.setContentType("text/html");
        PrintWriter out = servResponse.getWriter();
        out.println(jsonCountries.toString());
    }

    private void saveToDB(List<Country> countries) throws SQLException {
        String DBurl = "jdbc:postgresql://PostgreSQL/film2night";
        String DBuser = dotenv.get("PSQL_F2N_USER");
        String DBpass = dotenv.get("PSQL_F2N_PWD");

        Connection conn = DriverManager.getConnection(DBurl, DBuser, DBpass);
        conn.setAutoCommit(false);
        try (PreparedStatement pstmt = conn.prepareStatement(
                "INSERT INTO countries (\"country\") "
                        +
                        "VALUES (?)")) {
            for (int i = 0; i < countries.size(); i++) {
                pstmt.setString(1, countries.get(i).country());
                pstmt.addBatch();
                if (i + 1 % BATCH_SIZE == 0 || i == countries.size() - 1) {
                    try {
                        pstmt.executeBatch();
                        conn.commit();
                    } catch (BatchUpdateException ex) {
                        System.out.println(ex);
                        conn.rollback();
                    }
                }
            }
        }
        conn.close();
    }
}
