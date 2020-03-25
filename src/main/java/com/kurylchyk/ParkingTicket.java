package com.kurylchyk;

public class ParkingTicket {

    private Customer customer;  //composition
    private Vehicle vehicle; // composition
    //private DateTime from;
    private Integer sum;

    public ParkingTicket() {

        customer = new Customer();
        vehicle = new Vehicle();
    }
}





