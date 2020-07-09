package com.kurylchyk.model.services.impl.vehicleCommand;

import com.kurylchyk.model.dao.VehicleDAOFactory;
import com.kurylchyk.model.dao.VehicleDataUtil;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.services.impl.Command;
import com.kurylchyk.model.dao.vehicles.VehicleDAO;
import com.kurylchyk.model.exceptions.NoSuchVehicleFoundException;
import com.kurylchyk.model.vehicles.VehicleType;
import com.kurylchyk.model.vehicles.Vehicle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetVehicleCommand implements Command<Vehicle> {
    private VehicleDAO vehicleDAO;
    private String licensePlate;
    private static final Logger logger = LogManager.getLogger(GetVehicleCommand.class);

    public GetVehicleCommand(String licensePlate, VehicleType typeOfVehicle) {

        this.licensePlate = licensePlate;
        vehicleDAO = VehicleDAOFactory.getVehicleDAO(typeOfVehicle);
    }


    @Override
    public Vehicle execute() throws ParkingSystemException {
        logger.debug("Retrieving vehicle from database");
        try {
            return (Vehicle) vehicleDAO.select(licensePlate).get();
        }catch (Exception exception){
           throw new NoSuchVehicleFoundException("No vehicle with license plate " + licensePlate + " was found");
        }

    }
}
