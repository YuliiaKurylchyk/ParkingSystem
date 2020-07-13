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
import com.kurylchyk.model.services.ParkingSlotService;
import com.kurylchyk.model.services.VehicleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Saving ticket test")
public class SavingTicketTest {

    @Mock
    ParkingTicketDAO parkingTicketDAO;
    @Mock
    VehicleService vehicleService;
    @Mock
    CustomerService customerService;

    @Mock
    ParkingSlotService parkingSlotService;

    @InjectMocks
    SaveParkingTicketCommand command;

    private ParkingTicket parkingTicket;
    private Vehicle vehicle;
    private Customer customer;
    private ParkingSlot parkingSlot;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
         customer = Customer.newCustomer()
                .setCustomerID(1).setName("Name")
                .setSurname("Surname").setPhoneNumber("+380931092894").buildCustomer();

         vehicle = new Motorbike("Harley Davidson", "Iron 883", "BK 3242 IO");

         parkingSlot = new ParkingSlot(1, SlotSize.MEDIUM, SlotStatus.OCCUPIED, 15);

        parkingTicket = ParkingTicket.newParkingTicket()
                .withCustomer(customer)
                .withVehicle(vehicle)
                .withParkingSlot(parkingSlot)
                .withStatus(Status.PRESENT)
                .withArrivalTime(LocalDateTime.now().minusDays(5))
                .buildTicket();

        command = new SaveParkingTicketCommand(parkingTicket,parkingTicketDAO,parkingSlotService,vehicleService,customerService);
    }


    @Test
    @DisplayName("Should save parking ticket,vehicle,customer and update status")
    public void shouldSave() throws ParkingSystemException {
        Integer expectedID=1;
        when(vehicleService.saveToDB(vehicle)).thenReturn(vehicle);
        ParkingSlot occupiedSlot = parkingSlot;
        occupiedSlot.setStatus(SlotStatus.OCCUPIED);
        when(parkingSlotService.updateStatus(parkingSlot,SlotStatus.OCCUPIED)).thenReturn(occupiedSlot);

        when(customerService.saveToDB(customer)).thenReturn(customer);

        doNothing().when(vehicleService).connectCustomerToVehicle(vehicle,customer);

        when(parkingTicketDAO.insert(parkingTicket)).thenReturn(expectedID);

        ParkingTicket savedTicket = command.execute();


        assertAll(
                ()->assertNotNull(savedTicket),
                ()->assertEquals(expectedID,savedTicket.getParkingTicketID()),
                ()->assertEquals(vehicle,savedTicket.getVehicle()),
                ()->assertEquals(customer,savedTicket.getCustomer()),
                ()->assertEquals(occupiedSlot,savedTicket.getParkingSlot()),
                ()->assertNull(savedTicket.getDepartureTime()),
                ()->assertNotNull(savedTicket.getArrivalTime()),
                ()->assertNull(savedTicket.getCost())
        );

        verify(vehicleService).saveToDB(vehicle);
        verify(parkingSlotService).updateStatus(parkingSlot,SlotStatus.OCCUPIED);
        verify(customerService).saveToDB(customer);
        verify(vehicleService).connectCustomerToVehicle(vehicle,customer);
        verify(parkingTicketDAO).insert(parkingTicket);

    }
}
