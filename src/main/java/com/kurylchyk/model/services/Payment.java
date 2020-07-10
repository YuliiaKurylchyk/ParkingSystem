package com.kurylchyk.model.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;

public class Payment {

    private static final Logger logger = LogManager.getLogger(Payment.class);
    public  static BigDecimal calculatePrice(Long days, Integer pricePerDay){
        logger.info("Calculation of the price");
        if(days==0) {
            return new BigDecimal(pricePerDay);
        } else {
            return  new BigDecimal(days*pricePerDay);
        }

    }
}
