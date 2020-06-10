package com.kurylchyk.model.services;


public interface ServiceFactory {

    ParkingTicketService forParkingTicket();
    VehicleService forVehicle();
    CustomerService forCustomer();
}
