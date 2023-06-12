package com.timfralou.app;

import com.timfralou.app.postgresql.PostgreDB;
import io.github.cdimascio.dotenv.Dotenv;
import java.sql.SQLException;
import java.sql.ResultSet;

/**
 * App
 *
 */
public class App {
    public static void main(String[] args) {
        // DBurl and dotenv paths inside docker container
        Dotenv dotenv = Dotenv
                .configure()
                .directory("/usr/local/lib")
                .filename("env")
                .load();

        String DBurl = "jdbc:postgresql://PostgreSQL/film2night";
        String DBuser = dotenv.get("PSQL_F2N_USER");
        String DBpass = dotenv.get("PSQL_F2N_PWD");
        System.out.println("App started");
        PostgreDB postgreDB = new PostgreDB(DBurl, DBuser, DBpass);
        try (ResultSet rs = postgreDB.selectQuery("SELECT 5;")) {
            while (rs.next()) {
                System.out.println(rs.getInt(1));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("App running...");
        while (true) {

        }
    }
}