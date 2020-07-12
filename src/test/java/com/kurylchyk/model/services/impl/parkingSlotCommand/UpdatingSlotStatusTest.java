package com.kurylchyk.model.services.impl.parkingSlotCommand;

import com.kurylchyk.model.dao.ParkingSlotDAO;
import com.kurylchyk.model.domain.parkingSlots.ParkingSlot;
import com.kurylchyk.model.domain.parkingSlots.slotEnum.SlotSize;
import com.kurylchyk.model.domain.parkingSlots.slotEnum.SlotStatus;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.services.impl.parkingSlotDTO.ParkingSlotDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UpdatingSlotStatusTest {


    @Mock
    private ParkingSlotDAO parkingSlotDAO;

    @Mock
    private ParkingSlotDTO parkingSlotDTO;

    @InjectMocks
    private UpdateSlotStatusCommand command;

    private SlotStatus expectedStatus;
    private ParkingSlot parkingSlot;


    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        parkingSlot = new ParkingSlot(1, SlotSize.MEDIUM, SlotStatus.VACANT, 15);
    }

    @Test
    @DisplayName("Should change status to occupied")
    public void shouldChangeStatusToOccupied() throws ParkingSystemException {
        expectedStatus = SlotStatus.OCCUPIED;
        command = new UpdateSlotStatusCommand(parkingSlot, expectedStatus,parkingSlotDTO, parkingSlotDAO);

        doNothing().when(parkingSlotDAO).update(parkingSlot,parkingSlotDTO);

        ParkingSlot updatedSlot = command.execute();

        assertAll(
                () -> assertNotNull(updatedSlot),
                ()->assertSame(parkingSlot,updatedSlot),
                () -> assertEquals(expectedStatus, updatedSlot.getStatus())
        );

        verify(parkingSlotDAO).update(parkingSlot,parkingSlotDTO);
    }


    @Test
    @DisplayName("Should change status to vacant")
    public void shouldChangeStatusToVacant() throws ParkingSystemException {
        expectedStatus = SlotStatus.VACANT;
        parkingSlot.setStatus(SlotStatus.OCCUPIED);
        command = new UpdateSlotStatusCommand(parkingSlot, expectedStatus,parkingSlotDTO, parkingSlotDAO);

        doNothing().when(parkingSlotDAO).update(parkingSlot,parkingSlotDTO);

        ParkingSlot updatedSlot = command.execute();

        assertAll(
                () -> assertNotNull(updatedSlot),
                ()->assertSame(parkingSlot,updatedSlot),
                () -> assertEquals(expectedStatus, updatedSlot.getStatus())
        );

        verify(parkingSlotDAO).update(parkingSlot,parkingSlotDTO);
    }
}