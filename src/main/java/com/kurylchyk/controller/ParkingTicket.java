package com.kurylchyk.controller;

import com.kurylchyk.model.*;

public class ParkingTicket {

    private String ParkingTickedID;
    private Customer customer;
    private Vehicle vehicle;
    private Payment payment;
    private ParkingPlace parkingPlace;
    private TimeCheck timer = new TimeCheck();

    public ParkingTicket(Vehicle vehicle, ParkingPlace parkingPlace, Customer customer) {
        this.vehicle = vehicle;
        this.parkingPlace = parkingPlace;
        this.customer = customer;
        timer.start();

    }

    public int getParkingPlace() {
        return parkingPlace.getPlaceNumber();
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void getPayment() {
        timer.end();

        System.out.println("It is cost for you:");
        System.out.println(payment.calculatePrice(timer.countOfHours(), TypeOfVehicle.PASSANGER_CAR));

    }

    public String toString() {

        return "The vehicle: " + vehicle + "\n"
                + "Place #" + parkingPlace.getPlaceNumber()
                + "Data and time :" + timer + "\n"
                + "Customer: " + customer + "\n";
    }
}
