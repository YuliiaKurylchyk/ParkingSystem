package com.kurylchyk.model.services.impl;

import com.kurylchyk.model.services.*;

public class ServiceFacade {

    public static ParkingTicketService forParkingTicket() {
        return new ParkingTicketServiceImpl();
    }


    public static VehicleService forVehicle() {
        return new VehicleServiceImpl();
    }


    public static CustomerService forCustomer() {
        return new CustomerServiceImpl();
    }


    public static ParkingSlotService forParkingSlot() {
        return new ParkingSlotServiceImpl();
    }


}
