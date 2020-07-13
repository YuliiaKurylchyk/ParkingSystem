package com.kurylchyk.model.services.impl.ticketCommand;

import com.kurylchyk.model.dao.ParkingTicketDAO;
import com.kurylchyk.model.domain.customer.Customer;
import com.kurylchyk.model.domain.parkingSlots.ParkingSlot;
import com.kurylchyk.model.domain.parkingSlots.slotEnum.SlotSize;
import com.kurylchyk.model.domain.parkingSlots.slotEnum.SlotStatus;
import com.kurylchyk.model.domain.parkingTicket.ParkingTicket;
import com.kurylchyk.model.domain.parkingTicket.ticketEnum.Status;
import com.kurylchyk.model.domain.vehicles.Car;
import com.kurylchyk.model.domain.vehicles.Vehicle;
import com.kurylchyk.model.domain.vehicles.vehicleEnum.CarSize;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

public class UpdatingTicketTest {


    @Mock
    ParkingTicketDAO parkingTicketDAO;

    @InjectMocks
    UpdateTicketCommand command;

    private ParkingTicket parkingTicket;

    @BeforeEach
    public void init() {

        MockitoAnnotations.initMocks(this);
        Customer customer = Customer.newCustomer()
                .setCustomerID(1).setName("Name")
                .setSurname("Surname").setPhoneNumber("+38093104694").buildCustomer();

        Vehicle vehicle = new Car("Honda", "Crosstour", "BK 7956 IO", CarSize.EXCLUSIVE_CAR);

        ParkingSlot parkingSlot = new ParkingSlot(1, SlotSize.MEDIUM, SlotStatus.OCCUPIED, 15);

        parkingTicket = ParkingTicket.newParkingTicket()
                .withParkingTicketID(1)
                .withCustomer(customer)
                .withVehicle(vehicle)
                .withParkingSlot(parkingSlot)
                .withStatus(Status.PRESENT)
                .withArrivalTime(LocalDateTime.now().minusDays(5))
                .buildTicket();
    }

    @Test
    @DisplayName("Parking ticket should be updated in db but not changed")
    public void shouldBeUpdated() throws ParkingSystemException {
        command = new UpdateTicketCommand(parkingTicket,parkingTicketDAO);
        ParkingTicket returnedTicket = command.execute();

        assertAll(
                ()-> assertNotNull(returnedTicket),
                ()->assertSame(parkingTicket,returnedTicket,()->"Do not have same ref"),
                ()->assertEquals(parkingTicket,returnedTicket));
        verify(parkingTicketDAO).update(parkingTicket,parkingTicket.getParkingTicketID());

    }


}
