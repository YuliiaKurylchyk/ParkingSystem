package com.kurylchyk.model.services.impl.parkingSlotCommand;

import com.kurylchyk.model.dao.ParkingSlotDAO;

import com.kurylchyk.model.domain.parkingSlots.ParkingSlot;
import com.kurylchyk.model.domain.parkingSlots.slotEnum.SlotSize;
import com.kurylchyk.model.domain.parkingSlots.slotEnum.SlotStatus;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AddingNewSlotTest {

    @Mock
    private ParkingSlotDAO parkingSlotDAO;

    @InjectMocks
    private AddParkingSlotCommand command;

    private SlotSize slotSize;
    private SlotStatus slotStatus;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        slotSize = SlotSize.SMALL;
        slotStatus = SlotStatus.VACANT;

    }

    @Test
    @DisplayName("Should add new parking slot to data base")
    public void shouldAddNewSlot() throws ParkingSystemException {
        Integer lastID = 14;
        command = new AddParkingSlotCommand(slotSize, slotStatus,parkingSlotDAO);
        when(parkingSlotDAO.getLastID(Matchers.any(SlotSize.class))).thenReturn(lastID);
        ParkingSlot createdParkingSlot =  new ParkingSlot(lastID+1, slotSize, slotStatus);
        doAnswer(
                (args) -> {
                    assertEquals(args.getArguments()[0],createdParkingSlot);
                    return  null;
                }).when(parkingSlotDAO).insert(createdParkingSlot);
        command.execute();
    }



}
