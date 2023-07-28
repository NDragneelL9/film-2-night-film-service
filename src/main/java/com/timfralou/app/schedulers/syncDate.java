package com.timfralou.app.schedulers;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class syncDate {
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public String now() {
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    public Timestamp nowAsTsT() {
        Timestamp nowTsT = Timestamp.valueOf(dtf.format(LocalDateTime.now()));
        return nowTsT;
    }

    public Timestamp minuteAgo() {
        LocalDateTime minuteAgo = LocalDateTime.now().minus(1, ChronoUnit.MINUTES);
        return Timestamp.valueOf(dtf.format(minuteAgo));
    }

    public Timestamp twoDaysAgo() {
        LocalDateTime minuteAgo = LocalDateTime.now().minus(2, ChronoUnit.DAYS);
        return Timestamp.valueOf(dtf.format(minuteAgo));
    }
}
