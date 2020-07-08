package com.kurylchyk.model.services.impl.vehicleCommand;

import com.kurylchyk.model.dao.VehicleDAOFactory;
import com.kurylchyk.model.dao.VehicleDataUtil;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.services.impl.Command;
import com.kurylchyk.model.customer.Customer;
import com.kurylchyk.model.dao.vehicles.VehicleDAO;
import com.kurylchyk.model.vehicles.Vehicle;

public class ConnectCustomerToVehicleCommand  implements Command<Void> {

    private Customer customer;
    private Vehicle vehicle;

    public ConnectCustomerToVehicleCommand(Vehicle vehicle, Customer customer){
        this.vehicle = vehicle;
        this.customer = customer;

    }

    @Override
    public Void execute() throws ParkingSystemException {
        VehicleDataUtil.updateCustomerID(vehicle.getLicensePlate(),customer.getCustomerID());
        return  null;
    }
}
