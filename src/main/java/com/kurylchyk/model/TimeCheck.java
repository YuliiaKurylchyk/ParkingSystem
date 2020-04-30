package com.kurylchyk.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;

public class TimeCheck {


    public static  LocalDateTime getTime() {
        return LocalDateTime.now();
    }


    public static  long countOfHours(LocalDateTime from, LocalDateTime to) {

        return ChronoUnit.HOURS.between(from, to);
    }



}
