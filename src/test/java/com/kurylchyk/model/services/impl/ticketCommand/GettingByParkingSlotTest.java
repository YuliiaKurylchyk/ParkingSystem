package com.kurylchyk.model.services.impl.ticketCommand;

import com.kurylchyk.model.dao.ParkingTicketDAO;
import com.kurylchyk.model.domain.parkingSlots.ParkingSlot;
import com.kurylchyk.model.domain.parkingSlots.slotEnum.SlotSize;
import com.kurylchyk.model.domain.parkingSlots.slotEnum.SlotStatus;
import com.kurylchyk.model.domain.parkingTicket.ParkingTicket;
import com.kurylchyk.model.exceptions.ParkingSystemException;
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

@DisplayName("Should get ticket by parking slot information")
public class GettingByParkingSlotTest {

    @Mock
    ParkingTicketDAO parkingTicketDAO;

    @InjectMocks
    GetByParkingSlotCommand command;


    private ParkingSlotDTO parkingSlotDTO;
    private ParkingTicket  parkingTicket;
    private ParkingSlot expectedParkingSlot;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        parkingSlotDTO = new ParkingSlotDTO(SlotSize.SMALL, 12);
        parkingTicket = new ParkingTicket();
        expectedParkingSlot=    new ParkingSlot(parkingSlotDTO.getParkingSlotID(),
                parkingSlotDTO.getSlotSize(),
                SlotStatus.OCCUPIED);
        parkingTicket.setParkingSlot(expectedParkingSlot);
    }


    @Test
    @DisplayName("Should get parking ticket by parking slot")
    public void shouldGetParkingTicket() throws ParkingSystemException {

        command = new GetByParkingSlotCommand(parkingSlotDTO,parkingTicketDAO);

        when(parkingTicketDAO.selectByParkingSlot(parkingSlotDTO)).thenReturn(Optional.ofNullable(parkingTicket));

        ParkingTicket selectedParkingTicket = command.execute();

        assertAll(
                () -> assertNotNull(selectedParkingTicket),
                () -> assertEquals(expectedParkingSlot,parkingTicket.getParkingSlot() )
        );
        verify(parkingTicketDAO).selectByParkingSlot(parkingSlotDTO);
    }


}
