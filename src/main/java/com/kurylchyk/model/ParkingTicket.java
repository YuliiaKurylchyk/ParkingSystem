package com.kurylchyk.model;

import com.kurylchyk.model.parkingSlots.ParkingSlot;
import com.kurylchyk.model.vehicles.Vehicle;

public class ParkingTicket {


    private String parkingTickedID;
    private Customer customer;
    private Vehicle vehicle;
    private Payment payment;
    private ParkingSlot parkingSlot;
    private TimeCheck timer;
    private String status;

    {
        timer = new TimeCheck();
        payment = new Payment();

    }

    public ParkingTicket(int numberOfParkingTicket, Vehicle vehicle, ParkingSlot parkingSlot, Customer customer) {


        this.vehicle = vehicle;
        this.parkingSlot = parkingSlot;
        this.customer = customer;
        this.status = "on parking place";
        timer.start();
        parkingTickedID = createID(numberOfParkingTicket);

    }

    public String getParkingTickedID(){
        return  parkingTickedID;
    }

    public void setStatus(boolean stat) {
        status = (stat) ? status : "Left";
    }

    private String createID(int numberOfParkingTicket) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(vehicle.getMake().charAt(0))
                .append(vehicle.getModel().charAt(0))
                .append(parkingSlot.getSlotID())
                .append("_" + numberOfParkingTicket);
        return stringBuilder.toString();
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public Customer getCustomer(){
        return customer;
    }
    public ParkingSlot getParkingSlot() {
        return parkingSlot;
    }

    public void getPayment() {
        timer.end();
        System.out.println("Time from " + timer.getStartTime());
        System.out.println("Time to " + timer.getEndTime());
        System.out.println("It is cost for you: " +
                payment.calculatePrice(timer.countOfHours(), parkingSlot.getSizeOfSlot()));


    }

    public String toString() {

        return "ParkingTicketID: " + parkingTickedID + "\n"
                + "The vehicle: " + vehicle + "\n"
                + "Place №" + parkingSlot + "\n"
                + "Data and time : " + timer.getStartTime() + "\n"
                + "Customer: " + customer + "\n"
                + "Status: " + status;
    }


    @Override
    public int hashCode() {
        int result = 17;
        // result = 31 * result + parkingTickedID.hashCode();
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
        if (this.parkingTickedID.equals(anotherParkingTicket.parkingTickedID)
                && this.parkingSlot.equals(anotherParkingTicket.parkingSlot)
                && this.vehicle.equals(anotherParkingTicket.vehicle)
                && this.customer.equals(anotherParkingTicket.customer)) {
            return true;
        } else {
            return false;
        }
    }


}
