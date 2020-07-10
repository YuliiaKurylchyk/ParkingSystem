package com.kurylchyk.model.services;

import com.kurylchyk.model.services.Payment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;


public class PaymentTest {

    @Test
    @DisplayName("Calculation price per zero day")
    public void shouldCalculatePerZeroDay() {

        Long days = 0L;
        Integer pricePerDay = 15;
        BigDecimal expectedSum = new BigDecimal(15);
        BigDecimal actualSum = Payment.calculatePrice(days, pricePerDay);

        assertEquals(expectedSum, actualSum, () -> "The sum should have been " + expectedSum + " but was " + actualSum);
    }

    @ParameterizedTest
    @ValueSource(longs = {3L,5L,8l,10l})
    @DisplayName("Should calculate prices according to given values")
    public void shouldCalculatePrices(Long days) {

        Integer pricePerDay = 15;
        BigDecimal expectedSum = new BigDecimal(15*days);
        BigDecimal actualSum = Payment.calculatePrice(days,pricePerDay);
        assertEquals(expectedSum, actualSum, () -> "The sum should have been " + expectedSum + " but was " + actualSum);
    }

}
