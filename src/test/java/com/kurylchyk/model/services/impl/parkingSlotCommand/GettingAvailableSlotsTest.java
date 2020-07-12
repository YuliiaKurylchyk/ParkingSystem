package com.kurylchyk.model.services.impl.parkingSlotCommand;

import com.kurylchyk.model.dao.ParkingSlotDAO;
import com.kurylchyk.model.domain.parkingSlots.ParkingSlot;

import java.util.ArrayList;
import java.util.List;

import com.kurylchyk.model.domain.parkingSlots.slotEnum.SlotSize;
import com.kurylchyk.model.domain.parkingSlots.slotEnum.SlotStatus;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.services.impl.Command;
import com.sun.deploy.uitoolkit.impl.awt.AWTDragHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GettingAvailableSlotsTest {

    @Mock
    private ParkingSlotDAO parkingSlotDAO;

    @InjectMocks
    private GetAvailableSlotsCommand command;

    private List<ParkingSlot> expectedSlots;


    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        expectedSlots = new ArrayList<>();

    }

    @Test
    @DisplayName("Should get list of available small slots")
    public void shouldGetAvailableSmallSlots() throws ParkingSystemException {
        SlotSize slotSize = SlotSize.SMALL;
        expectedSlots.add(new ParkingSlot(1, SlotSize.SMALL, SlotStatus.VACANT));
        expectedSlots.add(new ParkingSlot(2, SlotSize.SMALL, SlotStatus.VACANT));
        command = new GetAvailableSlotsCommand(slotSize, parkingSlotDAO);

        when(parkingSlotDAO.selectAvailable(slotSize)).thenReturn(expectedSlots);
        List<ParkingSlot> selectedSlot = command.execute();

        assertAll(
                () -> assertNotNull(selectedSlot),
                () -> assertIterableEquals(expectedSlots, selectedSlot)
        );
        verify(parkingSlotDAO).selectAvailable(slotSize);
    }

    @Test
    @DisplayName("Should get list of available medium slots")
    public void shouldGetAvailableMediumSlots() throws ParkingSystemException {
        SlotSize slotSize = SlotSize.MEDIUM;
        expectedSlots.add(new ParkingSlot(1, slotSize, SlotStatus.VACANT));
        expectedSlots.add(new ParkingSlot(2, slotSize, SlotStatus.VACANT));
        command = new GetAvailableSlotsCommand(slotSize, parkingSlotDAO);

        when(parkingSlotDAO.selectAvailable(slotSize)).thenReturn(expectedSlots);
        List<ParkingSlot> selectedSlot = command.execute();

        assertAll(
                () -> assertNotNull(selectedSlot),
                () -> assertIterableEquals(expectedSlots, selectedSlot)
        );
        verify(parkingSlotDAO).selectAvailable(slotSize);
    }


    @Test
    @DisplayName("Should get list of available large slots")
    public void shouldGetAvailableLargeSlots() throws ParkingSystemException {
        SlotSize slotSize = SlotSize.LARGE;
        expectedSlots.add(new ParkingSlot(1, slotSize, SlotStatus.VACANT));
        expectedSlots.add(new ParkingSlot(2, slotSize, SlotStatus.VACANT));
        expectedSlots.add(new ParkingSlot(3, slotSize, SlotStatus.VACANT));
        expectedSlots.add(new ParkingSlot(4, slotSize, SlotStatus.VACANT));
        command = new GetAvailableSlotsCommand(slotSize, parkingSlotDAO);

        when(parkingSlotDAO.selectAvailable(slotSize)).thenReturn(expectedSlots);
        List<ParkingSlot> selectedSlot = command.execute();

        assertAll(
                () -> assertNotNull(selectedSlot),
                () -> assertIterableEquals(expectedSlots, selectedSlot)
        );
        verify(parkingSlotDAO).selectAvailable(slotSize);
    }

}
