package com.kurylchyk.model.services.impl.vehicleCommand;

import com.kurylchyk.model.services.impl.Command;
import com.kurylchyk.model.customer.Customer;
import com.kurylchyk.model.dao.VehicleDAO;
import com.kurylchyk.model.vehicles.Vehicle;

public class ConnectCustomerToVehicleCommand  implements Command<Void> {
    private VehicleDAO vehicleDAO = new VehicleDAO();
    private Customer customer;
    private Vehicle vehicle;

    public ConnectCustomerToVehicleCommand(Vehicle vehicle, Customer customer){
        this.vehicle = vehicle;
        this.customer = customer;
    }


    @Override
    public Void execute() throws Exception {
        vehicleDAO.updateCustomerID(vehicle.getLicencePlate(),customer.getCustomerID());
        return  null;
    }
}
