package com.kurylchyk.model;


import com.kurylchyk.model.parkingSlots.ParkingSlot;
import com.kurylchyk.model.vehicles.Vehicle;

import java.time.LocalDateTime;

public class ParkingTicket {

    private Integer parkingTicketID;
    private Customer customer;
    private Vehicle vehicle;
    private ParkingSlot parkingSlot;
    private LocalDateTime from_time;
    private LocalDateTime to_time;
    private Payment payment = new Payment();
    private String status;

    public void setParkingTicketID(Integer parkingTicketID) {
        this.parkingTicketID = parkingTicketID;
    }


    public ParkingTicket(Vehicle vehicle, ParkingSlot parkingSlot, Customer customer) {

        this.vehicle = vehicle;
        this.parkingSlot = parkingSlot;
        this.customer = customer;
        this.status = "present";
        from_time = TimeCheck.getTime();
    }
    public ParkingTicket(){

    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public void setParkingSlot(ParkingSlot parkingSlot) {
        this.parkingSlot = parkingSlot;
    }

    public void setFrom_time(LocalDateTime from_time) {
        this.from_time = from_time;
    }

    public void setTo_time(LocalDateTime to_time) {
        this.to_time = to_time;
    }

    public void setStatus(String stat) {
        status = stat;
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

    public Double getPayment() {
        to_time = TimeCheck.getTime();
        return payment.calculatePrice(TimeCheck.countOfHours(from_time,to_time), parkingSlot.getSizeOfSlot());
    }


    public String toString() {

        return "The vehicle: " + vehicle + "\n"
                + "Place №" + parkingSlot + "\n"
                + "Data and time : " + from_time + "\n"
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
