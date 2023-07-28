package com.timfralou.app.schedulers;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class F2nScheduler implements ServletContextListener {
    private ScheduledExecutorService scheduler;

    public ScheduledExecutorService scheduler() {
        return this.scheduler;
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.schedule(new FilmFiltersJob(), 1, TimeUnit.MINUTES);
        scheduler.scheduleAtFixedRate(new TopFilmsJob(), 1, 48, TimeUnit.HOURS);
        scheduler.scheduleAtFixedRate(new StaleFilmsJob(), 2, 48, TimeUnit.HOURS);
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        scheduler.shutdownNow();
    }
}
