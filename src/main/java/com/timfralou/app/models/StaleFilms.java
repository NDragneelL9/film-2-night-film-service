package com.timfralou.app.models;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.timfralou.app.interfaces.KinopoiskAPI;
import com.timfralou.app.schedulers.syncDate;

public class StaleFilms {
    private final Connection dbConn;
    private final KinopoiskAPI knpApi;

    public StaleFilms(Connection dbConn, KinopoiskAPI knpApi) {
        this.dbConn = dbConn;
        this.knpApi = knpApi;
    }

    public void syncStaleFilms() {
        try {
            PreparedStatement pstmt = dbConn
                    .prepareStatement("SELECT \"kinopoiskId\" from films where \"lastSync\" < ?;");
            pstmt.setTimestamp(1, new syncDate().twoDaysAgo());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String filmId = String.valueOf(rs.getInt("kinopoiskId"));
                String filmJson = knpApi.getFilm(filmId);
                Film film = new ObjectMapper().readValue(filmJson, Film.class);
                film.updateInDB(dbConn);
            }
        } catch (SQLException | IOException ex) {
            ex.printStackTrace();
        }
    }
}
