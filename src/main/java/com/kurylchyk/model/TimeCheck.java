package com.kurylchyk.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class TimeCheck {


    public static  LocalDateTime getTime() {
        return LocalDateTime.now();
    }


    public static  long countOfDays(LocalDateTime from, LocalDateTime to) {

        return  ChronoUnit.DAYS.between(from, to);
    }



}
