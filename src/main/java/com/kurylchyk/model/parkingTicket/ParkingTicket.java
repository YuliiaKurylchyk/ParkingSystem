package com.kurylchyk.model.parkingTicket;

import com.kurylchyk.model.customer.Customer;
import com.kurylchyk.model.parkingSlots.ParkingSlot;
import com.kurylchyk.model.vehicles.Vehicle;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ParkingTicket {

    private Integer parkingTicketID;
    private Customer customer;
    private Vehicle vehicle;
    private ParkingSlot parkingSlot;
    private LocalDateTime arrivalTime;
    private LocalDateTime leftTime;
    private BigDecimal cost;
    private Status status;

    public ParkingTicket(ParkingTicketBuilder builder){
        this.parkingTicketID = builder.parkingTicketID;
        this.vehicle = builder.vehicle;
        this.customer = builder.customer;
        this.parkingSlot = builder.parkingSlot;
        this.status = builder.status;
        this.arrivalTime = builder.arrivalTime;
        this.leftTime = builder.leftTime;
        this.cost = builder.cost;
    }

    public void setParkingTicketID(Integer parkingTicketID) {
        this.parkingTicketID = parkingTicketID;
    }


    public static ParkingTicketBuilder newParkingTicket(){
        return new ParkingTicketBuilder();
    }
    public ParkingTicket(){
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public void setParkingSlot(ParkingSlot parkingSlot) {
        this.parkingSlot = parkingSlot;
    }


    public void setLeftTime(LocalDateTime leftTime) {
        this.leftTime = leftTime;
    }

    public void setStatus(Status status) {
        this.status = status;
    }


    public Integer getParkingTicketID(){
        return parkingTicketID;
    }
    public Vehicle getVehicle() {
        return vehicle;
    }

    public Customer getCustomer() {
        return customer;
    }

    public ParkingSlot getParkingSlot() {
        return parkingSlot;
    }

    public BigDecimal getCost() {
       return cost;
    }

    public Status getStatus() {
        return status;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public LocalDateTime getLeftTime(){
        return leftTime;
    }

    @Override
    public String toString() {

        return "The vehicle: " + vehicle + "\n"
                + "Place №" + parkingSlot + "\n"
                + "Data and time : " + arrivalTime + "\n"
                + "Customer: " + customer + "\n"
                + "Status: " + status;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + customer.hashCode();
        result = 31 * result + vehicle.hashCode();
        result = 31 * result + parkingSlot.hashCode();

        return result;

    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }

        ParkingTicket anotherParkingTicket = (ParkingTicket) obj;
        if (this.parkingTicketID == parkingTicketID
                && this.parkingSlot.equals(anotherParkingTicket.parkingSlot)
                && this.vehicle.equals(anotherParkingTicket.vehicle)
                && this.customer.equals(anotherParkingTicket.customer)) {
            return true;
        } else {
            return false;
        }
    }
}
