package com.kurylchyk.model.services.impl.parkingSlotCommand;

import com.kurylchyk.model.dao.ParkingSlotDAO;
import com.kurylchyk.model.domain.parkingSlots.ParkingSlot;
import com.kurylchyk.model.domain.parkingSlots.slotEnum.SlotSize;
import com.kurylchyk.model.domain.parkingSlots.slotEnum.SlotStatus;
import com.kurylchyk.model.exceptions.CurrentSlotIsOccupiedException;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class DeletingSlotTest {

    @Mock
    private ParkingSlotDAO parkingSlotDAO;

    @InjectMocks
    private DeleteParkingSlotCommand command;

    private ParkingSlot parkingSlot;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        parkingSlot = new ParkingSlot(
                1,
                SlotSize.MEDIUM,
                SlotStatus.VACANT,
                25);
    }

    @Test
    @DisplayName("Should delete parking slot from data base")
    public void shouldAddNewSlot() throws ParkingSystemException {
        command = new DeleteParkingSlotCommand(parkingSlot, parkingSlotDAO);
        doAnswer(
                (args) -> {
                    assertEquals(args.getArguments()[0], parkingSlot);
                    return null;
                }).when(parkingSlotDAO).delete(parkingSlot);
        command.execute();
        verify(parkingSlotDAO).delete(parkingSlot);
    }

    @Test
    @DisplayName("Should throw an exception")
    public void shouldThrowAnException() {
        parkingSlot.setStatus(SlotStatus.OCCUPIED);
        command = new DeleteParkingSlotCommand(parkingSlot, parkingSlotDAO);

        assertThrows(CurrentSlotIsOccupiedException.class, () -> command.execute());
        verify(parkingSlotDAO, never()).delete(parkingSlot);
    }


}
