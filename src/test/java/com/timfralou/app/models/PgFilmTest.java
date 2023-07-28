package com.timfralou.app.models;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import com.timfralou.app.BasicTest;
import com.timfralou.app.seeds.FilmSeed;

public class PgFilmTest extends BasicTest {

    @Test
    public void checksPgFilmById() throws SQLException {
        new FilmSeed().film().saveToDB(dbTEST.connect());
        Film film = new PgFilm(dbTEST.connect()).pgFilmById(448);
        assertTrue(film.toString().startsWith("Film [kinopoiskId=448"));
        dbTEST.updateQuery("delete from films");
    }

    @Test
    public void checksPgToFilm() throws SQLException {
        new FilmSeed().film().saveToDB(dbTEST.connect());
        PreparedStatement pstmt = dbTEST.connect()
                .prepareStatement("SELECT * FROM films where \"kinopoiskId\" = ?");
        pstmt.setInt(1, 448);
        ResultSet rs = pstmt.executeQuery();
        Film film = new Film();
        while (rs.next()) {
            film = new PgFilm(dbTEST.connect()).pgToFilm(rs);
        }
        assertTrue(film.toString().startsWith("Film [kinopoiskId=448"));
        dbTEST.updateQuery("delete from films");
    }
}