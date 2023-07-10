package com.timfralou.app.postgresql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import io.github.cdimascio.dotenv.Dotenv;

public class PostgreDB {
    private Dotenv dotenv;
    private String DBurl;
    private String DBuser;
    private String DBpassword;
    private Connection dbConn;

    public PostgreDB() {
        // keep
    }

    public PostgreDB(dbType DB_TYPE) {
        switch (DB_TYPE) {
            case MAIN:
                this.dotenv = Dotenv.configure()
                        .directory("/usr/local/")
                        .filename("env")
                        .load();
                this.DBuser = dotenv.get("PSQL_F2N_USER");
                this.DBpassword = dotenv.get("PSQL_F2N_PWD");
                this.DBurl = dotenv.get("PSQL_F2N_DB_URL");
                break;
            case TEST:
                this.dotenv = Dotenv.configure()
                        .filename(".env")
                        .load();
                this.DBuser = dotenv.get("PSQL_F2N_USER");
                this.DBpassword = dotenv.get("PSQL_F2N_PWD");
                this.DBurl = dotenv.get("PSQL_LOCAL_TEST_URL");
                break;
            default:
                System.out.println("Invalid DB TYPE");
                break;
        }
        try {
            this.dbConn = DriverManager.getConnection(DBurl, DBuser, DBpassword);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public Connection conn() {
        return dbConn;
    }

    public int updateQuery(String sqlQuery) {
        int rowsUpdated = -1;
        try {
            PreparedStatement stmt = dbConn.prepareStatement(sqlQuery);
            rowsUpdated = stmt.executeUpdate();
            dbConn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rowsUpdated;
    }
}
