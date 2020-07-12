package com.kurylchyk.model.services.impl.parkingSlotCommand;

import com.kurylchyk.model.dao.ParkingSlotDAO;
import com.kurylchyk.model.domain.parkingSlots.ParkingSlot;
import com.kurylchyk.model.domain.parkingSlots.slotEnum.SlotSize;
import com.kurylchyk.model.domain.parkingSlots.slotEnum.SlotStatus;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.services.impl.Command;
import com.kurylchyk.model.services.impl.parkingSlotDTO.ParkingSlotDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GettingSlotTest {

    @Mock
    private ParkingSlotDAO parkingSlotDAO;

    @InjectMocks
    private GetParkingSlotCommand command;
    private ParkingSlotDTO parkingSlotDTO;
    private ParkingSlot expectedSlot;


    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        parkingSlotDTO = new ParkingSlotDTO(SlotSize.SMALL,12);
        expectedSlot = new ParkingSlot(parkingSlotDTO.getParkingSlotID(),parkingSlotDTO.getSlotSize(),
        SlotStatus.VACANT,15);
    }


    @Test
    @DisplayName("Should get parking slot from data base")
    public void shouldAddNewSlot() throws ParkingSystemException {
        command = new GetParkingSlotCommand(parkingSlotDTO,parkingSlotDAO);
        when(parkingSlotDAO.select(parkingSlotDTO)).thenReturn(Optional.ofNullable(expectedSlot));
        ParkingSlot selectedSlot =  command.execute();

        assertAll(
                ()->assertNotNull(selectedSlot,"Should not return null"),
                ()->assertEquals(expectedSlot,selectedSlot)
        );

        verify(parkingSlotDAO).select(parkingSlotDTO);
    }


}
