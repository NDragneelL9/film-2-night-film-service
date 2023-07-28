package com.timfralou.app.schedulers;

import java.io.IOException;
import com.timfralou.app.models.FilmFilters;
import com.timfralou.app.servlets.BaseServlet;

public class FilmFiltersJob implements Runnable {

    @Override
    public void run() {
        BaseServlet servlet = new BaseServlet();
        servlet.init();
        try {
            FilmFilters filmFilters = new FilmFilters(servlet.dbConn());
            filmFilters.syncFilmFilters(servlet.knpApi());
            System.out.println("Film filters updated: " + new syncDate().now());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
