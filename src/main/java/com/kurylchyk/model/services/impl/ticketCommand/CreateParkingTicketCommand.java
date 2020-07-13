package com.kurylchyk.model.services.impl.ticketCommand;

import com.kurylchyk.model.domain.customer.Customer;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.domain.parkingSlots.ParkingSlot;
import com.kurylchyk.model.domain.parkingTicket.ParkingTicket;
import com.kurylchyk.model.domain.parkingTicket.ticketEnum.Status;
import com.kurylchyk.model.services.impl.Command;
import com.kurylchyk.model.domain.vehicles.Vehicle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;

/**
 * Creates parking ticket with Builder
 */
public class CreateParkingTicketCommand implements Command<ParkingTicket> {
    private Vehicle vehicle;
    private Customer customer;
    private  ParkingSlot parkingSlot;
    private static final Logger logger = LogManager.getLogger(CreateParkingTicketCommand.class);
    public CreateParkingTicketCommand(Vehicle vehicle, Customer customer, ParkingSlot parkingSlot){
        this.vehicle = vehicle;
        this.customer = customer;
        this.parkingSlot =parkingSlot;
    }

    @Override
    public ParkingTicket execute() throws ParkingSystemException {

        ParkingTicket parkingTicket = ParkingTicket.newParkingTicket()
                .withVehicle(vehicle)
                .withCustomer(customer)
                .withParkingSlot(parkingSlot)
                .withArrivalTime(LocalDateTime.now())
                .withStatus(Status.PRESENT).buildTicket();
        logger.debug("New parking ticket was created");
        return parkingTicket;
    }
}
