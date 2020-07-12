package com.kurylchyk.model.services.impl.parkingSlotCommand;

import com.kurylchyk.model.dao.ParkingSlotDAO;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class UpdatingPricesTest {

    @Mock
    private ParkingSlotDAO parkingSlotDAO;

    @InjectMocks
    private UpdatePriceCommand command;


    @BeforeEach
    public void init() {

    }
}


