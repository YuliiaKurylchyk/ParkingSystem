package com.kurylchyk.model;


import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Timer {

    public static Long getTotalDays(LocalDateTime arrivalTime,LocalDateTime departureTime){

        Long countOfDays =  ChronoUnit.DAYS.between(arrivalTime,departureTime);
        if(countOfDays<=1) {
            return 1L;
        }else {
            return countOfDays;
        }
    }

}
