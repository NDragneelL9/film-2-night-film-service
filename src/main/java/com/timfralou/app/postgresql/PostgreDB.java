package com.timfralou.app.postgresql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import io.github.cdimascio.dotenv.Dotenv;

public class PostgreDB {
    private String DBurl;
    private String DBuser;
    private String DBpassword;
    private Connection dbConn;
    private dbType DB_TYPE;
    private Dotenv dotenv;

    public PostgreDB(dbType DB_TYPE, Dotenv dotenv) {
        this.DB_TYPE = DB_TYPE;
        this.dotenv = dotenv;
    }

    public Connection connect() throws SQLException {
        if (dbConn != null && !dbConn.isClosed()) {
            return dbConn;
        } else {
            this.dbConn = establishconnect();
            return dbConn;
        }
    }

    public int updateQuery(String sqlQuery) {
        int rowsUpdated = -1;
        try {
            this.connect();
            PreparedStatement stmt = dbConn.prepareStatement(sqlQuery);
            rowsUpdated = stmt.executeUpdate();
            dbConn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rowsUpdated;
    }

    public ResultSet selectQuery(String sqlQuery) throws SQLException {
        this.connect();
        Statement stmt = dbConn.createStatement();
        ResultSet rs = stmt.executeQuery(sqlQuery);
        dbConn.close();
        return rs;
    }

    private Connection establishconnect() throws SQLException {
        this.loadCreds(dotenv);
        switch (DB_TYPE) {
            case MAIN:
                this.DBurl = dotenv.get("PSQL_F2N_DB_URL");
                break;
            case TEST:
                this.DBurl = dotenv.get("PSQL_F2N_DB_TEST_URL");
                break;
            default:
                throw new SQLException("Invalid DB TYPE");
        }
        return DriverManager.getConnection(DBurl, DBuser, DBpassword);
    }

    private void loadCreds(Dotenv dotenv) {
        this.DBuser = dotenv.get("PSQL_F2N_USER");
        this.DBpassword = dotenv.get("PSQL_F2N_PWD");
    }
}
