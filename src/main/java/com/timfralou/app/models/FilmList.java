package com.timfralou.app.models;

import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.timfralou.app.postgresql.PostgreDB;
import com.timfralou.app.postgresql.dbType;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FilmList {
    private List<Film> filmList;
    private static final int BATCH_SIZE = 50;
    private static final String INSERT_FILMS = "INSERT INTO films " +
            "(\"kinopoiskId\", \"nameRu\", \"ratingKinopoisk\", \"ratingKinopoiskVoteCount\", \"year\", \"filmLength\") "
            + "VALUES (?, ?, ?, ?, ? ,?)";
    private static final String INSERT_FILMS_COUNTRIES = "INSERT INTO films_countries(film_id, country_id) " +
            "SELECT f.film_id, c.country_id FROM films as f, countries as c " +
            "WHERE f.\"kinopoiskId\" = ? AND c.\"country\" = ?";
    private static final String INSERT_FILMS_GENRES = "INSERT INTO films_genres(film_id, genre_id) " +
            "SELECT f.film_id, g.genre_id FROM films as f, genres as g " +
            "WHERE f.\"kinopoiskId\" = ? AND g.\"genre\" = ?";

    public FilmList() {
        // keep
    }

    public FilmList(List<Film> filmList) {
        this.filmList = filmList;
    }

    public List<Film> filmList() {
        return filmList;
    }

    public String saveToDB(dbType DB_TYPE) throws SQLException {
        PostgreDB db = new PostgreDB(DB_TYPE);
        Connection conn = db.conn();
        conn.setAutoCommit(false);

        insertFilms(conn);
        insertCountries(conn);
        insertGenres(conn);

        conn.close();
        return "Succesfully saved to " + DB_TYPE;
    }

    private void insertFilms(Connection conn) throws SQLException {
        PreparedStatement ins_filmList = conn.prepareStatement(INSERT_FILMS);
        for (int i = 0; i < filmList.size(); i++) {
            ins_filmList.setInt(1, filmList.get(i).kinopoiskId());
            ins_filmList.setString(2, filmList.get(i).nameRu());
            ins_filmList.setString(3, filmList.get(i).ratingKinopoisk());
            ins_filmList.setInt(4, filmList.get(i).ratingKinopoiskVoteCount());
            ins_filmList.setInt(5, filmList.get(i).year());
            ins_filmList.setInt(6, filmList.get(i).filmLength());
            ins_filmList.addBatch();
            if (i + 1 % BATCH_SIZE == 0 || i == filmList.size() - 1) {
                try {
                    ins_filmList.executeBatch();
                    conn.commit();
                } catch (BatchUpdateException ex) {
                    System.out.println(ex);
                    conn.rollback();
                }
            }
        }
    }

    private void insertCountries(Connection conn) throws SQLException {
        PreparedStatement ins_countries = conn.prepareStatement(INSERT_FILMS_COUNTRIES);
        for (int i = 0; i < filmList.size(); i++) {
            int fillmCountriesCount = filmList.get(i).countries().length;
            for (int j = 0; j < fillmCountriesCount; j++) {
                ins_countries.setInt(1, filmList.get(i).kinopoiskId());
                ins_countries.setString(2, filmList.get(i).countries()[j].country());
                ins_countries.executeUpdate();
            }
            conn.commit();
        }
    }

    private void insertGenres(Connection conn) throws SQLException {
        PreparedStatement ins_genres = conn.prepareStatement(INSERT_FILMS_GENRES);
        for (int i = 0; i < filmList.size(); i++) {
            int fillmGenresCount = filmList.get(i).genres().length;
            for (int j = 0; j < fillmGenresCount; j++) {
                ins_genres.setInt(1, filmList.get(i).kinopoiskId());
                ins_genres.setString(2, filmList.get(i).genres()[j].genre());
                ins_genres.executeUpdate();
            }
            conn.commit();
        }
    }
}
