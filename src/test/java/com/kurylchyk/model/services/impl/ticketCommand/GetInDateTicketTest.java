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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.mockito.MockitoAnnotations;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class GetInDateTicketTest {

    @Mock
    ParkingTicketDAO parkingTicketDAO;

    @InjectMocks
    GetInDateCommand command;

    private static LocalDateTime expectedDay = LocalDateTime.now();
    private static Status expectedStatus = Status.PRESENT;
    private static List<ParkingTicket> expectedListInAppropriateDay;
    private static List<ParkingTicket> expectedListInAppropriateDayAndStatus;

    @BeforeAll
    public static void initAll(){
        expectedListInAppropriateDay = new ArrayList<>(
                asList(
                        ParkingTicket.newParkingTicket().withArrivalTime(expectedDay).buildTicket(),
                        ParkingTicket.newParkingTicket().withArrivalTime(expectedDay).buildTicket(),
                        ParkingTicket.newParkingTicket().withArrivalTime(expectedDay).buildTicket()
                ));

        expectedListInAppropriateDayAndStatus = new ArrayList<>(
                asList(
                        ParkingTicket.newParkingTicket().withArrivalTime(expectedDay).withStatus(expectedStatus).buildTicket(),
                        ParkingTicket.newParkingTicket().withArrivalTime(expectedDay).withStatus(expectedStatus).buildTicket()
                ));
    }


    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);

        when(parkingTicketDAO.selectInDate(expectedDay)).thenReturn(expectedListInAppropriateDay);
        when(parkingTicketDAO.selectInDateAndStatus(expectedDay,expectedStatus)).thenReturn(expectedListInAppropriateDayAndStatus);


    }


    @Test
    @DisplayName("Should get list in appropriate day")
    public void shouldGetInAppropriateDay() throws ParkingSystemException {
        command = new GetInDateCommand(expectedDay,null,parkingTicketDAO);

        List<ParkingTicket> selectedTickets = command.execute();

        assertAll(
                ()->assertNotNull(selectedTickets),
                ()->assertEquals(expectedListInAppropriateDay,selectedTickets)

        );
        for (ParkingTicket pk: selectedTickets){
            assertTrue(pk.getArrivalTime().equals(expectedDay));
        }
        verify(parkingTicketDAO).selectInDate(expectedDay);
        verify(parkingTicketDAO,never()).selectInDateAndStatus(eq(expectedDay),any(Status.class));
    }

    @Test
    @DisplayName("Should get list in appropriate day")
    public void shouldGetInAppropriateDayAndStatus() throws ParkingSystemException {
        command = new GetInDateCommand(expectedDay,expectedStatus,parkingTicketDAO);

        List<ParkingTicket> selectedTickets = command.execute();

        assertAll(
                ()->assertNotNull(selectedTickets),
                ()->assertEquals(expectedListInAppropriateDayAndStatus,selectedTickets)
        );

        for (ParkingTicket pk: selectedTickets){
            assertTrue(pk.getArrivalTime().equals(expectedDay));
            assertTrue(pk.getStatus().equals(expectedStatus));
        }
        verify(parkingTicketDAO,never()).selectInDate(expectedDay);
        verify(parkingTicketDAO).selectInDateAndStatus(expectedDay,expectedStatus);
    }

}
