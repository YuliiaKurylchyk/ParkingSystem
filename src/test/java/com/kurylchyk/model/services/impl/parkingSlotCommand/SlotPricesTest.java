package com.kurylchyk.model.services.impl.parkingSlotCommand;

import com.kurylchyk.model.dao.ParkingSlotDAO;
import com.kurylchyk.model.domain.parkingSlots.slotEnum.SlotSize;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Should update parking slot prices")
public class SlotPricesTest {

    @Mock
    private ParkingSlotDAO parkingSlotDAO;

    private UpdatePriceCommand updateCommand;

    private GetSlotPricesCommand getCommand;

    private Map<SlotSize, Integer> prices;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);

        prices = new LinkedHashMap<>();
        prices.put(SlotSize.SMALL, 15);
        prices.put(SlotSize.MEDIUM, 25);
        prices.put(SlotSize.LARGE, 45);
    }


    @Test
    @DisplayName("Should update prices")
    public void shouldUpdatePrices() throws ParkingSystemException {

        updateCommand = new UpdatePriceCommand(prices, parkingSlotDAO);

        doAnswer(
                (args)->{
                        SlotSize expectedSlotSize = (SlotSize) args.getArguments()[0];
                        Integer expectedPrice = (Integer)args.getArguments()[1];
                        assertAll(
                                () -> assertNotNull(expectedSlotSize),
                                () -> assertNotNull(expectedPrice));
                        return null;
                    }).when(parkingSlotDAO).updatePrice(Matchers.any(SlotSize.class), Matchers.anyInt());
        updateCommand.execute();
        verify(parkingSlotDAO, times(3)).updatePrice(Matchers.any(SlotSize.class), anyInt());
    }

    @Test
    @DisplayName("Should get prices from database")
    public void shouldGetPrices() throws ParkingSystemException {

        getCommand = new GetSlotPricesCommand(parkingSlotDAO);

        Map<SlotSize, Integer> expectedPrices;
        when(parkingSlotDAO.getSlotsPrice()).thenReturn(prices);

        expectedPrices = getCommand.execute();
        assertAll(
                ()-> assertNotNull(expectedPrices),
                ()->assertTrue(expectedPrices.containsKey(SlotSize.SMALL)),
                ()->assertTrue(expectedPrices.containsKey(SlotSize.MEDIUM)),
                ()->assertTrue(expectedPrices.containsKey(SlotSize.LARGE)),
                ()-> assertTrue(expectedPrices.size()==3));

        verify(parkingSlotDAO).getSlotsPrice();
    }
}


