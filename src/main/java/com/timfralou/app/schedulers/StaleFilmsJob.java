package com.timfralou.app.schedulers;

import com.timfralou.app.models.StaleFilms;
import com.timfralou.app.servlets.FilmServlet;

public class StaleFilmsJob implements Runnable {
    @Override
    public void run() {
        FilmServlet servlet = new FilmServlet();
        servlet.init();
        StaleFilms staleFilms = new StaleFilms(servlet.dbConn(), servlet.knpApi());
        staleFilms.syncStaleFilms();
        System.out.println("Stale films updated: " + new syncDate().now());
    }
}
