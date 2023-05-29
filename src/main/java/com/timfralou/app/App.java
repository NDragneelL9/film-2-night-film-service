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
        String DBurl = "jdbc:postgresql://localhost:5432/film2night";
        String DBuser = "f2n_admin";
        String DBpass = "film2night";
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