package com.kurylchyk.model.services.impl.ticketCommand;


import com.kurylchyk.model.dao.ParkingTicketDAO;
import com.kurylchyk.model.domain.parkingTicket.ParkingTicket;
import com.kurylchyk.model.exceptions.NoSuchParkingTicketException;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Should get ticket by parking ticket id")
public class GettingTicketTest {


    @Mock
    ParkingTicketDAO parkingTicketDAO;

    @InjectMocks
    GetParkingTicketCommand parkingTicketCommand;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    ParkingTicket expectedParkingTicket;
    Integer expectedParkingTicketID;

    @BeforeEach
    public void initParkingTicket() {
        MockitoAnnotations.initMocks(this);
        expectedParkingTicketID=1;
        expectedParkingTicket = new ParkingTicket();
        expectedParkingTicket.setParkingTicketID(expectedParkingTicketID);
    }

    @Test
    @DisplayName("Should get parking ticket")
    public void shouldGetParkingTicket() throws NoSuchParkingTicketException {


        parkingTicketCommand = new GetParkingTicketCommand(expectedParkingTicketID, parkingTicketDAO);

        when(parkingTicketDAO
                .select(expectedParkingTicketID))
                .thenReturn(Optional.ofNullable(expectedParkingTicket));

        ParkingTicket selectedParkingTicket = parkingTicketCommand.execute();

        assertAll(
                () -> assertNotNull(selectedParkingTicket),
                () -> assertEquals(expectedParkingTicketID, selectedParkingTicket.getParkingTicketID())
        );

        verify(parkingTicketDAO).select(expectedParkingTicketID);
    }


    @Test
    @DisplayName("Should throw exception")
    public void shouldThrowException() {

        Integer wrongParkingTicketID = 124;
        parkingTicketCommand = new GetParkingTicketCommand(wrongParkingTicketID,parkingTicketDAO);
        when(parkingTicketDAO.select(wrongParkingTicketID)).thenThrow(NoSuchParkingTicketException.class);

        assertThrows(NoSuchParkingTicketException.class,()->parkingTicketCommand.execute());

        verify(parkingTicketDAO).select(wrongParkingTicketID);
    }

}
