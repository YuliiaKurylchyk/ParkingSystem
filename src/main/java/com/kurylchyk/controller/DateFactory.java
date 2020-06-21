package com.kurylchyk.controller;

import java.time.LocalDateTime;

public class DateFactory {

    public static LocalDateTime getAppropriateDate(String date) {

        LocalDateTime appropriateDate = null;
        switch (date) {
            case "today":
                appropriateDate = LocalDateTime.now();
                break;
            case "yesterday":
                appropriateDate = LocalDateTime.now().minusDays(1);
                break;
            case "oneWeekAgo":
                appropriateDate = LocalDateTime.now().minusWeeks(1);
                break;
            case "monthAgo":
                appropriateDate = LocalDateTime.now().minusMonths(1);
                break;

        }
        return appropriateDate;
    }

}
