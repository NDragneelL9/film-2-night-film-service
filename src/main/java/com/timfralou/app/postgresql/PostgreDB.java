package com.timfralou.app.postgresql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.ResultSet;

enum dbType {
    MAIN,
    TEST,
}

public class PostgreDB {
    private Dotenv dotenv;
    private String DBurl;
    private String DBuser;
    private String DBpassword;

    public PostgreDB() {
        // keep
    }

    public PostgreDB(String DB_TYPE) throws SQLException {
        switch (dbType.valueOf(DB_TYPE)) {
            case MAIN:
                this.dotenv = Dotenv.configure()
                        .directory("/usr/local/")
                        .filename("env")
                        .load();
                this.DBurl = dotenv.get("PSQL_F2N_DB_URL");
                this.DBuser = dotenv.get("PSQL_F2N_USER");
                this.DBpassword = dotenv.get("PSQL_F2N_PWD");
                break;
            case TEST:
                this.dotenv = Dotenv.configure()
                        .filename(".env")
                        .load();
                this.DBurl = dotenv.get("PSQL_LOCAL_TEST_URL");
                this.DBuser = dotenv.get("PSQL_F2N_USER");
                this.DBpassword = dotenv.get("PSQL_F2N_PWD");
                break;
            default:
                throw new SQLException("invalid DB TYPE");
        }
    }

    public ResultSet selectQuery(String sqlQuery) throws SQLException {
        Connection conn = connect();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sqlQuery);
        return rs;
    }

    public int updateQuery(String sqlQuery) throws SQLException {
        Connection conn = connect();
        PreparedStatement stmt = conn.prepareStatement(sqlQuery);
        int rowsUpdated = stmt.executeUpdate();
        return rowsUpdated;
    }

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(DBurl, DBuser, DBpassword);
    }
}
