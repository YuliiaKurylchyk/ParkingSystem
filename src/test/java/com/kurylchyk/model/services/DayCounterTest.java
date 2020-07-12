package com.kurylchyk.model.services;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DayCounterTest {


    @Test
    @DisplayName("Should calculate proper count of days")
    public void  shouldCalculateProperNumberOfDays() {
        LocalDateTime arrivalTime = LocalDateTime.now().minusDays(3);
        LocalDateTime departureTime = LocalDateTime.now();
        Long expectedNumberOfDays = 3L;

        Long actualNumberOfDays = DayCounter.getTotalDays(arrivalTime,departureTime);

        assertEquals(expectedNumberOfDays,actualNumberOfDays,()-> "Should have calculated");
    }

    @Test
    @DisplayName("Should calculate as 1 full day not 0 days ")
    public void  shouldCalculateAsOneFullDay() {
        LocalDateTime arrivalTime = LocalDateTime.now().minusHours(6);
        LocalDateTime departureTime = LocalDateTime.now();
        Long expectedNumberOfDays = 1L;
        Long actualNumberOfDays = DayCounter.getTotalDays(arrivalTime,departureTime);
        assertEquals(expectedNumberOfDays,actualNumberOfDays);
    }

}
