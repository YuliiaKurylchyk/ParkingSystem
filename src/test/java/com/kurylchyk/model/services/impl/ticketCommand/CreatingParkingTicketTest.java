package com.kurylchyk.model.services.impl.ticketCommand;

import com.kurylchyk.model.domain.customer.Customer;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.domain.parkingSlots.ParkingSlot;
import com.kurylchyk.model.domain.parkingSlots.slotEnum.SlotSize;
import com.kurylchyk.model.domain.parkingSlots.slotEnum.SlotStatus;
import com.kurylchyk.model.domain.parkingTicket.ParkingTicket;
import com.kurylchyk.model.domain.vehicles.Motorbike;
import com.kurylchyk.model.domain.vehicles.Vehicle;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Creating parking tickets")
public class CreatingParkingTicketTest {


    @Test
    @DisplayName("Should create parking ticket")
    public void shouldCreateParkingTicket() throws ParkingSystemException {

        Vehicle vehicle = new  Motorbike("Harley Davidson","Iron 883","AA 3329 KO");
        Customer customer = new Customer();
        customer.setCustomerID(1);
        customer.setName("Name");
        customer.setSurname(("Surname"));
        customer.setPhoneNumber("phoneNumber");
        ParkingSlot parkingSlot = new ParkingSlot(1, SlotSize.SMALL, SlotStatus.VACANT);
        CreateParkingTicketCommand creteCommand = new CreateParkingTicketCommand(vehicle,customer,parkingSlot);

        ParkingTicket parkingTicket = creteCommand.execute();

        assertNotNull(parkingTicket);

    }
}
