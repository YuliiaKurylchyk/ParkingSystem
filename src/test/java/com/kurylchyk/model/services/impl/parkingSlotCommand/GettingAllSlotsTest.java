package com.kurylchyk.model.services.impl.parkingSlotCommand;

import com.kurylchyk.model.dao.ParkingSlotDAO;
import com.kurylchyk.model.domain.parkingSlots.ParkingSlot;
import com.kurylchyk.model.domain.parkingSlots.slotEnum.SlotSize;
import com.kurylchyk.model.domain.parkingSlots.slotEnum.SlotStatus;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import net.bytebuddy.implementation.MethodCall;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GettingAllSlotsTest {

    @Mock
    private ParkingSlotDAO parkingSlotDAO;

    @InjectMocks
    private GetAllSlotsCommand command;

    private List<ParkingSlot> expectedSlots;


    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        expectedSlots = new ArrayList<>();

    }

    @Test
    @DisplayName("Should get list of all small slots")
    public void shouldGetAvailableSmallSlots() throws ParkingSystemException {

        SlotSize slotSize = SlotSize.SMALL;
        command = new GetAllSlotsCommand(slotSize,parkingSlotDAO);
        expectedSlots.add(new ParkingSlot(1,SlotSize.SMALL,SlotStatus.OCCUPIED,15));
        expectedSlots.add(new ParkingSlot(2,SlotSize.SMALL,SlotStatus.VACANT,15));
        expectedSlots.add(new ParkingSlot(3,SlotSize.SMALL,SlotStatus.OCCUPIED,15));

        when(parkingSlotDAO.selectAll(slotSize)).thenReturn(expectedSlots);
        List<ParkingSlot> selectedSlot = command.execute();

        assertAll(
                () -> assertNotNull(selectedSlot),
                () -> assertIterableEquals(expectedSlots, selectedSlot)
        );
        verify(parkingSlotDAO).selectAll(slotSize);
        verify(parkingSlotDAO,never()).selectAll();
    }



    @Test
    @DisplayName("Should get list of all slots")
    public void shouldGetAvailableAllSlots() throws ParkingSystemException {

        command = new GetAllSlotsCommand(null,parkingSlotDAO);
        expectedSlots.add(new ParkingSlot(1,SlotSize.SMALL,SlotStatus.OCCUPIED,15));
        expectedSlots.add(new ParkingSlot(2,SlotSize.SMALL,SlotStatus.VACANT,15));
        expectedSlots.add(new ParkingSlot(3,SlotSize.SMALL,SlotStatus.OCCUPIED,15));
        expectedSlots.add(new ParkingSlot(1,SlotSize.MEDIUM,SlotStatus.VACANT,25));
        expectedSlots.add(new ParkingSlot(2,SlotSize.MEDIUM,SlotStatus.OCCUPIED,25));

        when(parkingSlotDAO.selectAll()).thenReturn(expectedSlots);
        List<ParkingSlot> selectedSlot = command.execute();

        assertAll(
                () -> assertNotNull(selectedSlot),
                () -> assertIterableEquals(expectedSlots, selectedSlot)
        );
        verify(parkingSlotDAO,never()).selectAll(any(SlotSize.class));
        verify(parkingSlotDAO).selectAll();
    }

}
