package com.kurylchyk.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class TimeCheck {

    private static LocalDateTime from;
    private static LocalDateTime to;


    public void start() {
        from = LocalDateTime.now();
    }

    public void end() {
        to = LocalDateTime.now();
    }


    public String getStartTime(){
        return from.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    }

    public String getEndTime(){
        return to.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    }

    public long countOfHours() {
        if (to == null) {
            end();
        }
        return ChronoUnit.HOURS.between(from, to);
    }



}
