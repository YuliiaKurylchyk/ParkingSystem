package com.kurylchyk.model.services.impl.ticketCommand;

import com.kurylchyk.model.customer.Customer;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.parkingSlots.ParkingSlot;
import com.kurylchyk.model.parkingTicket.ParkingTicket;
import com.kurylchyk.model.parkingTicket.Status;
import com.kurylchyk.model.services.impl.Command;
import com.kurylchyk.model.vehicles.Vehicle;

import java.time.LocalDateTime;

public class CreateParkingTicketCommand implements Command<ParkingTicket> {
    private Vehicle vehicle;
    private Customer customer;
    private  ParkingSlot parkingSlot;
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
        return parkingTicket;
    }
}
