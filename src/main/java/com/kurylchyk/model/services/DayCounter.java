package com.kurylchyk.model.services;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Calculate the number of days spent on parking lot
 *
 */
public class DayCounter {

    private static final Logger logger= LogManager.getLogger(DayCounter.class);
    public static Long getTotalDays(LocalDateTime arrivalTime,LocalDateTime departureTime){

        logger.info("Calculation the number of days");
        Long countOfDays =  ChronoUnit.DAYS.between(arrivalTime,departureTime);
        if(countOfDays<=1) {
            return 1L;
        }else {
            return countOfDays;
        }
    }

}
