package com.timfralou.app.schedulers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import com.timfralou.app.BasicTest;
import com.timfralou.app.seeds.FilmSeed;

public class StaleFilmsJobTest extends BasicTest {
    @Test
    public void checksRun() throws SQLException {
        new FilmSeed().film().saveToDB(dbTEST.connect());
        new StaleFilmsJob().run();
        PreparedStatement pstmt = dbTEST.connect()
                .prepareStatement("SELECT \"nameRu\" from films where \"lastSync\" > ?;");
        pstmt.setTimestamp(1, new syncDate().minuteAgo());
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            assertEquals("Форрест Гамп", rs.getString("nameRu"));
        }
    }

    @AfterAll
    public static void cleanUp() {
        dbTEST.updateQuery("delete from films;");
    }
}
