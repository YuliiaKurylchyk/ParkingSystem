package com.kurylchyk.model.parkingTicket;

import com.kurylchyk.model.customer.Customer;
import com.kurylchyk.model.parkingSlots.ParkingSlot;
import com.kurylchyk.model.vehicles.Vehicle;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ParkingTicketBuilder {

    protected Integer parkingTicketID;
    protected Vehicle vehicle;
    protected Customer customer;
    protected ParkingSlot parkingSlot;
    protected LocalDateTime arrivalTime;
    protected LocalDateTime leftTime;
    protected BigDecimal cost;
    protected Status status;


    public ParkingTicketBuilder withParkingTicketID(Integer id) {
        parkingTicketID = id;
        return this;
    }

    public ParkingTicketBuilder withVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
        return this;
    }

    public ParkingTicketBuilder withParkingSlot(ParkingSlot parkingSlot) {
        this.parkingSlot = parkingSlot;
        return this;
    }

    public ParkingTicketBuilder withCustomer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public ParkingTicketBuilder withArrivalTime(LocalDateTime localDateTime) {
        arrivalTime = localDateTime;
        return this;
    }

    public ParkingTicketBuilder withLeftTime(LocalDateTime localDateTime) {
        leftTime = localDateTime;
        return this;
    }

    public ParkingTicketBuilder withCost(BigDecimal cost) {
        this.cost = cost;
        return this;
    }

    public ParkingTicketBuilder withStatus(Status status) {
        this.status = status;
        return this;
    }


    public ParkingTicket buildTicket() {
        return new ParkingTicket(this);
    }


}
