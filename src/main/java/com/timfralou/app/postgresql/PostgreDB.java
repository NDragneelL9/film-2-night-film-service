package com.timfralou.app.postgresql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;

public class PostgreDB {

    private String DBurl;
    private String DBuser;
    private String DBpassword;

    private String DEFAULT_URL = "jdbc:postgresql://localhost:5432/postgres";
    private String DEFAULT_USER = "postgres";

    public PostgreDB(final String url, final String user, final String password) {
        this.DBurl = url.isBlank() ? DEFAULT_URL : url;
        this.DBuser = user.isBlank() ? DEFAULT_USER : user;
        this.DBpassword = password;
    }

    public ResultSet selectQuery(String sqlQuery) throws SQLException {
        Connection conn = DriverManager.getConnection(DBurl, DBuser, DBpassword);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sqlQuery);
        return rs;
    }

    public int updateQuery(String sqlQuery) throws SQLException {
        Connection conn = DriverManager.getConnection(DBurl, DBuser, DBpassword);
        PreparedStatement stmt = conn.prepareStatement(sqlQuery);
        int rowsUpdated = stmt.executeUpdate();
        return rowsUpdated;
    }
}
