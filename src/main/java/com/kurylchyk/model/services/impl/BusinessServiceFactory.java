package com.kurylchyk.model.services.impl;

import com.kurylchyk.model.services.CustomerService;
import com.kurylchyk.model.services.ParkingTicketService;
import com.kurylchyk.model.services.ServiceFactory;
import com.kurylchyk.model.services.VehicleService;

public class BusinessServiceFactory implements ServiceFactory {
    @Override
    public ParkingTicketService forParkingTicket() {
        return  new ParkingTicketServiceImpl();
    }

    @Override
    public VehicleService forVehicle() {
        return new VehicleServiceImpl();
    }

    @Override
    public CustomerService forCustomer() {
        return new CustomerServiceImpl();
    }
}
