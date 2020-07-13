package com.kurylchyk.model.services.impl.ticketCommand;

import com.kurylchyk.model.dao.ParkingTicketDAO;
import com.kurylchyk.model.domain.customer.Customer;
import com.kurylchyk.model.domain.parkingSlots.ParkingSlot;
import com.kurylchyk.model.domain.parkingSlots.slotEnum.SlotSize;
import com.kurylchyk.model.domain.parkingSlots.slotEnum.SlotStatus;
import com.kurylchyk.model.domain.parkingTicket.ParkingTicket;
import com.kurylchyk.model.domain.parkingTicket.ticketEnum.Status;
import com.kurylchyk.model.domain.vehicles.Motorbike;
import com.kurylchyk.model.domain.vehicles.Vehicle;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.services.CustomerService;
import com.kurylchyk.model.services.VehicleService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import static org.mockito.Mockito.*;

@DisplayName("Deleting parking ticket test")
public class DeletingTicketTest {


    @Mock
    ParkingTicketDAO parkingTicketDAO;
    @Mock
    VehicleService vehicleService;
    @Mock
    CustomerService customerService;

    @InjectMocks
    DeleteTicketCompletely command;

    private  static ParkingTicket parkingTicket;

    @BeforeAll
    public static  void initAll(){
        Customer customer = Customer.newCustomer()
                .setCustomerID(1).setName("Name")
                .setSurname("Surname").setPhoneNumber("+380931092894").buildCustomer();

        Vehicle vehicle = new Motorbike("Harley Davidson","Iron 883","BK 3242 IO");

        ParkingSlot parkingSlot = new ParkingSlot(1, SlotSize.MEDIUM, SlotStatus.OCCUPIED,15);

        parkingTicket = ParkingTicket.newParkingTicket()
                .withParkingTicketID(1)
                .withCustomer(customer)
                .withVehicle(vehicle)
                .withParkingSlot(parkingSlot)
                .withStatus(Status.PRESENT)
                .withArrivalTime(LocalDateTime.now().minusDays(5))
                .withDepartureTime(LocalDateTime.now())
                .withCost(new BigDecimal(15))
                .buildTicket();


    }


    @BeforeEach
    public void init() throws ParkingSystemException {
        MockitoAnnotations.initMocks(this);
        command =new DeleteTicketCompletely(parkingTicket,parkingTicketDAO,vehicleService,customerService);

        doNothing().when(parkingTicketDAO).delete(parkingTicket);
        when(parkingTicketDAO.selectByVehicleID(parkingTicket.getVehicle().getLicensePlate())).thenReturn(Optional.ofNullable(null));
        when(vehicleService.deleteCompletely(parkingTicket.getVehicle())).thenReturn(parkingTicket.getVehicle());
        when(parkingTicketDAO.selectByCustomerID(parkingTicket.getCustomer().getCustomerID())).thenReturn(new ArrayList<>());
        doNothing().when(customerService).deleteCompletely(parkingTicket.getCustomer());
    }

    @Test
    @DisplayName("Should delete parking ticket,vehicle and customer")
    public void shouldDeleteAllInfo() throws ParkingSystemException {


        command.execute();

        verify(parkingTicketDAO).delete(parkingTicket);
        verify(parkingTicketDAO).selectByVehicleID(parkingTicket.getVehicle().getLicensePlate());
        verify(parkingTicketDAO).selectByCustomerID(parkingTicket.getCustomer().getCustomerID());
        verify(vehicleService).deleteCompletely(parkingTicket.getVehicle());
        verify(customerService).deleteCompletely(parkingTicket.getCustomer());
    }


    @Test
    @DisplayName("Should delete parking ticket, customer but not vehicle")
    public void shouldNoDeleteVehicle() throws ParkingSystemException {

        when(parkingTicketDAO.selectByVehicleID(parkingTicket.getVehicle().getLicensePlate())).thenReturn(Optional.ofNullable(parkingTicket));

        command.execute();

        verify(parkingTicketDAO).delete(parkingTicket);
        verify(parkingTicketDAO).selectByVehicleID(parkingTicket.getVehicle().getLicensePlate());
        verify(parkingTicketDAO).selectByCustomerID(parkingTicket.getCustomer().getCustomerID());
        verify(vehicleService,never()).deleteCompletely(parkingTicket.getVehicle());
        verify(customerService).deleteCompletely(parkingTicket.getCustomer());
    }

    @Test
    @DisplayName("Should delete parking ticket,vehicle but not customer")
    public void shouldNotDeleteCustomer() throws ParkingSystemException {

        ArrayList<ParkingTicket> tickets = new ArrayList<>(Arrays.asList(parkingTicket));

        when(parkingTicketDAO.selectByCustomerID(parkingTicket.getCustomer().getCustomerID())).thenReturn(tickets);

        command.execute();

        verify(parkingTicketDAO).delete(parkingTicket);
        verify(parkingTicketDAO).selectByVehicleID(parkingTicket.getVehicle().getLicensePlate());
        verify(parkingTicketDAO).selectByCustomerID(parkingTicket.getCustomer().getCustomerID());
        verify(vehicleService).deleteCompletely(parkingTicket.getVehicle());
        verify(customerService,never()).deleteCompletely(parkingTicket.getCustomer());
    }
}
