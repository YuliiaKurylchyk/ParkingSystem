package com.kurylchyk.model;

import java.math.BigDecimal;

public class Payment {
    public  static BigDecimal calculatePrice(Long days, Integer pricePerDay){

        if(days==0) {
            return new BigDecimal(pricePerDay);
        } else {
            return  new BigDecimal(days*pricePerDay);
        }

    }
}
