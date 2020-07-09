package com.kurylchyk.model.services.impl.vehicleCommand;

import com.kurylchyk.model.Connector;
import com.kurylchyk.model.dao.VehicleDAOFactory;
import com.kurylchyk.model.dao.VehicleDataUtil;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.services.impl.Command;
import com.kurylchyk.model.customer.Customer;
import com.kurylchyk.model.dao.vehicles.VehicleDAO;
import com.kurylchyk.model.vehicles.Vehicle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConnectCustomerToVehicleCommand  implements Command<Void> {

    private Customer customer;
    private Vehicle vehicle;
    private static final Logger logger = LogManager.getLogger(ConnectCustomerToVehicleCommand.class);

    public ConnectCustomerToVehicleCommand(Vehicle vehicle, Customer customer){
        this.vehicle = vehicle;
        this.customer = customer;

    }

    @Override
    public Void execute() throws ParkingSystemException {
        logger.debug("Setting customerID to vehicle");
        VehicleDataUtil.updateCustomerID(vehicle.getLicensePlate(),customer.getCustomerID());
        return  null;
    }
}
