package com.timfralou.app.schedulers;

import java.io.IOException;
import com.timfralou.app.models.TopFilms;
import com.timfralou.app.servlets.BaseServlet;

public class TopFilmsJob implements Runnable {

    @Override
    public void run() {
        BaseServlet servlet = new BaseServlet();
        servlet.init();
        try {
            TopFilms topFilms = new TopFilms(servlet.dbConn());
            topFilms.syncTopFilms(servlet.knpApi());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
