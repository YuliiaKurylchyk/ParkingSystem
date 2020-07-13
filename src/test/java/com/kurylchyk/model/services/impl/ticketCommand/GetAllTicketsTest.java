package com.kurylchyk.model.services.impl.ticketCommand;

import com.kurylchyk.model.dao.ParkingTicketDAO;
import com.kurylchyk.model.domain.parkingTicket.ParkingTicket;
import com.kurylchyk.model.domain.parkingTicket.ticketEnum.Status;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class GetAllTicketsTest {

    @Mock
    private ParkingTicketDAO parkingTicketDAO;

    @InjectMocks
    private GetAllTicketsCommand command;

    private static Status expectedStatus = Status.PRESENT;

    private static List<ParkingTicket> expectedListOfAllTickets;

    private static List<ParkingTicket> expectedListWithAppropriateStatus;


    @BeforeAll
    public static void initAll(){
        expectedListOfAllTickets = new ArrayList<>(
                asList(
                        ParkingTicket.newParkingTicket().withStatus(expectedStatus).buildTicket(),
                        ParkingTicket.newParkingTicket().withStatus(expectedStatus).buildTicket(),
                        ParkingTicket.newParkingTicket().withStatus(Status.LEFT).buildTicket()
                ));

        expectedListWithAppropriateStatus = new ArrayList<>(
                asList(
                        ParkingTicket.newParkingTicket().withStatus(expectedStatus).buildTicket(),
                        ParkingTicket.newParkingTicket().withStatus(expectedStatus).buildTicket()
                ));

    }

    @BeforeEach
    public void init() {

        MockitoAnnotations.initMocks(this);

        when(parkingTicketDAO.selectAll(Status.PRESENT)).thenReturn(expectedListWithAppropriateStatus);
        when(parkingTicketDAO.selectAll()).thenReturn(expectedListOfAllTickets);
    }



    @Test
    @DisplayName("Should get tickets that are present")
    public void shouldGetPresentTickets() throws ParkingSystemException {
        command = new GetAllTicketsCommand(Status.PRESENT,parkingTicketDAO);
        List<ParkingTicket> selectedTickets  =command.execute();

        assertAll(
                ()->assertNotNull(selectedTickets),
                ()->assertTrue(selectedTickets.size()<=expectedListOfAllTickets.size())
        );

        for (ParkingTicket pk: selectedTickets){
            assertTrue(pk.getStatus().equals(expectedStatus));
        }
        verify(parkingTicketDAO).selectAll(Status.PRESENT);
        verify(parkingTicketDAO,never()).selectAll();
    }

    @Test
    @DisplayName("Should get all tickets")
    public void shouldGetAllTickets() throws ParkingSystemException {
        command = new GetAllTicketsCommand(null,parkingTicketDAO);
        List<ParkingTicket> selectedTickets  =command.execute();

        assertAll(
                ()->assertNotNull(selectedTickets),
                ()->assertEquals(selectedTickets,expectedListOfAllTickets)
        );


        verify(parkingTicketDAO,never()).selectAll(Status.PRESENT);
        verify(parkingTicketDAO).selectAll();
    }

}
