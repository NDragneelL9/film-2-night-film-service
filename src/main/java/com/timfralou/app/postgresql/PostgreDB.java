package com.timfralou.app.postgresql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import io.github.cdimascio.dotenv.Dotenv;

public class PostgreDB {
    private Dotenv dotenv;
    private String DBurl;
    private String DBuser;
    private String DBpassword;

    public PostgreDB() {
        // keep
    }

    public PostgreDB(dbType DB_TYPE) throws SQLException {
        switch (DB_TYPE) {
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
                String osName = System.getProperty("os.name");
                boolean IS_OS_LINUX = osName.toLowerCase().startsWith("linux");
                this.DBurl = IS_OS_LINUX ? dotenv.get("PSQL_LOCAL_LNX_TEST_URL")
                        : dotenv.get("PSQL_LOCAL_WSL_TEST_URL");
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
        conn.close();
        return rs;
    }

    public int updateQuery(String sqlQuery) throws SQLException {
        Connection conn = connect();
        PreparedStatement stmt = conn.prepareStatement(sqlQuery);
        int rowsUpdated = stmt.executeUpdate();
        conn.close();
        return rowsUpdated;
    }

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(DBurl, DBuser, DBpassword);
    }
}
