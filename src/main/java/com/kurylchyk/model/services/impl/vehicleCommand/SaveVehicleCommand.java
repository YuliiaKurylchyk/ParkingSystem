package com.kurylchyk.model.services.impl.vehicleCommand;

import com.kurylchyk.model.dao.VehicleDAOFactory;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.services.impl.Command;
import com.kurylchyk.model.dao.vehicles.VehicleDAO;
import com.kurylchyk.model.domain.vehicles.Vehicle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SaveVehicleCommand implements Command<Void> {
    private static final Logger logger = LogManager.getLogger(SaveVehicleCommand.class);
    private VehicleDAO vehicleDAO;
    private Vehicle vehicle;

    public SaveVehicleCommand(Vehicle vehicle){

        this.vehicle = vehicle;
        vehicleDAO = VehicleDAOFactory.getVehicleDAO(vehicle.getVehicleType());
    }

    @Override
    public Void execute() throws ParkingSystemException {
        vehicleDAO.insert(vehicle);
        logger.info("Vehicle "+vehicle + " was successfully saved to database");
        return  null;
    }
}
