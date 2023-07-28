package com.timfralou.app.schedulers;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import org.junit.jupiter.api.Test;
import com.timfralou.app.BasicTest;
import jakarta.servlet.ServletContextEvent;

public class F2nSchedulerTest extends BasicTest {

    @Test
    public void checksContextInitialized() {
        F2nScheduler f2nScheduler = new F2nScheduler();
        ServletContextEvent event = mock(ServletContextEvent.class);
        f2nScheduler.contextInitialized(event);
        f2nScheduler.contextDestroyed(event);
        assertTrue(f2nScheduler.scheduler().isShutdown());
    }
}
