package com.kurylchyk.model.services.impl.ticketCommand;


import com.kurylchyk.model.dao.ParkingTicketDAO;
import com.kurylchyk.model.domain.customer.Customer;
import java.util.ArrayList;
import java.util.List;
import com.kurylchyk.model.domain.parkingTicket.ParkingTicket;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Should select ticket by customer info")
public class GettingByCustomerTest {

    @Mock
    ParkingTicketDAO parkingTicketDAO;

    @InjectMocks
    GetByCustomerCommand command;


    private Customer expectedCustomer;
    private ParkingTicket parkingTicket;
    private List<ParkingTicket> expectedParkingTickets;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        expectedCustomer = Customer.newCustomer()
                .setCustomerID(2)
                .setName("Name")
                .setSurname("Surname")
                .setPhoneNumber("+380931092585")
                .buildCustomer();
        parkingTicket = new ParkingTicket();
        parkingTicket.setCustomer(expectedCustomer);
        expectedParkingTickets = new ArrayList<>();
    }


    @Test
    @DisplayName("Should return list of expected parking tickets")
    public void shouldGetParkingTickets() throws ParkingSystemException {

        expectedParkingTickets.add(parkingTicket);
        command = new GetByCustomerCommand(expectedCustomer.getCustomerID(),parkingTicketDAO);

        when(parkingTicketDAO.selectByCustomerID(expectedCustomer.getCustomerID()))
                .thenReturn(expectedParkingTickets);

       List<ParkingTicket> selectedParkingTickets = command.execute();

        assertAll(
                () -> assertNotNull(selectedParkingTickets),
                () -> assertFalse(selectedParkingTickets.isEmpty()),
                () -> assertIterableEquals(expectedParkingTickets,selectedParkingTickets)
        );
        verify(parkingTicketDAO).selectByCustomerID(expectedCustomer.getCustomerID());
    }

    @Test
    @DisplayName("Should get parking ticket by parking slot")
    public void shouldGetEmptyList() throws ParkingSystemException {
        Integer wrongCustomerID = 456;
        expectedCustomer.setCustomerID(wrongCustomerID);
        command = new GetByCustomerCommand(expectedCustomer.getCustomerID(),parkingTicketDAO);

        when(parkingTicketDAO.selectByCustomerID(expectedCustomer.getCustomerID()))
                .thenReturn(expectedParkingTickets);

        List<ParkingTicket> selectedParkingTickets = command.execute();

        assertAll(
                () -> assertNotNull(selectedParkingTickets),
                () -> assertTrue(selectedParkingTickets.isEmpty())
        );
        verify(parkingTicketDAO).selectByCustomerID(expectedCustomer.getCustomerID());
    }


}
