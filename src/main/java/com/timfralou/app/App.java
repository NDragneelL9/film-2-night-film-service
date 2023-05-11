package com.timfralou.app;

import com.timfralou.app.postgresql.PostgreDB;
import java.sql.SQLException;
import java.sql.ResultSet;

/**
 * App
 *
 */
public class App {
    public static void main(String[] args) {
        String DBurl = "jdbc:postgresql://localhost:5432/postgres";
        String DBuser = "postgres";
        String DBpass = "postgres";
        PostgreDB postgreDB = new PostgreDB(DBurl, DBuser, DBpass);
        try (ResultSet rs = postgreDB.selectQuery("SELECT 5;")) {
            while (rs.next()) {
                System.out.println(rs.getInt(1));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}