package com.timfralou.app.postgresql;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import com.timfralou.app.BasicTest;

public class PostgreDBTest extends BasicTest {

    @Test
    public void checksInvalidDBType() {
        PostgreDB invalidDB = new PostgreDB(dbType.UNKNOWN, localEnv);
        try {
            invalidDB.connect();
        } catch (SQLException ex) {
            assertEquals("Invalid DB TYPE", ex.getMessage());
        }
    }

    @Test
    public void worksMainDB() throws SQLException {
        ResultSet rs = dbMAIN.selectQuery("SELECT 1;");
        if (rs.next()) {
            assertEquals(1, rs.getInt(1));
        }
    }

    @Test
    public void worksTestDB() throws SQLException {
        ResultSet rs = dbTEST.selectQuery("SELECT 2;");
        if (rs.next()) {
            assertEquals(2, rs.getInt(1));
        }

    }
}
