package com.timfralou.app.postgresql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.ResultSet;

public class PostgreDB {
    private static final Dotenv dotenv = Dotenv.configure()
            .directory("/usr/local/tomcat/webapps")
            .filename("env")
            .load();
    private String DBurl = dotenv.get("PSQL_F2N_DB_URL");
    private String DBuser = dotenv.get("PSQL_F2N_USER");
    private String DBpassword = dotenv.get("PSQL_F2N_PWD");

    public PostgreDB() {
        // keep
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
