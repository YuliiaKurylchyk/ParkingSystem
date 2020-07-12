package com.kurylchyk.model.services.impl.vehicleCommand;

import com.kurylchyk.model.dao.VehicleDataUtil;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.services.impl.Command;
import com.kurylchyk.model.domain.customer.Customer;
import com.kurylchyk.model.domain.vehicles.Vehicle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConnectCustomerToVehicleCommand  implements Command<Void> {

    private Customer customer;
    private Vehicle vehicle;
    private VehicleDataUtil vehicleDataUtil = new VehicleDataUtil();
    private static final Logger logger = LogManager.getLogger(ConnectCustomerToVehicleCommand.class);

    public ConnectCustomerToVehicleCommand(Vehicle vehicle, Customer customer){
        this.vehicle = vehicle;
        this.customer = customer;
    }
    ConnectCustomerToVehicleCommand(Vehicle vehicle, Customer customer,VehicleDataUtil vehicleDataUtil){
        this.vehicle = vehicle;
        this.customer = customer;
        this.vehicleDataUtil = vehicleDataUtil;
    }


    @Override
    public Void execute() throws ParkingSystemException {
        logger.debug("Setting customerID to vehicle");
        vehicleDataUtil.updateCustomerID(vehicle.getLicensePlate(),customer.getCustomerID());
        return  null;
    }
}
