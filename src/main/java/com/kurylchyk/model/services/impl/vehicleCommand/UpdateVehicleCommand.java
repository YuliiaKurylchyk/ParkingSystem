package com.kurylchyk.model.services.impl.vehicleCommand;

import com.kurylchyk.model.dao.VehicleDAOFactory;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.exceptions.SuchVehiclePresentException;
import com.kurylchyk.model.services.impl.Command;
import com.kurylchyk.model.dao.vehicles.VehicleDAO;
import com.kurylchyk.model.services.impl.CommandExecutor;
import com.kurylchyk.model.services.impl.ticketCommand.UpdateVehicleInTicketCommand;
import com.kurylchyk.model.services.impl.utilVehicle.VehicleInfo;
import com.kurylchyk.model.vehicles.Vehicle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UpdateVehicleCommand implements Command<Vehicle> {

    private VehicleDAO vehicleDAO;
    private VehicleInfo vehicleInfo;
    private String currentLicensePlate;
    private CommandExecutor executor = new CommandExecutor();
    private static final Logger logger  = LogManager.getLogger(UpdateVehicleCommand.class);


    public UpdateVehicleCommand(VehicleInfo vehicleInfo, String licencePlate) {

        this.vehicleInfo = vehicleInfo;
        this.currentLicensePlate = licencePlate;
        vehicleDAO = VehicleDAOFactory.getVehicleDAO(vehicleInfo.getVehicleType());
    }

    @Override
    public Vehicle execute() throws ParkingSystemException {

        Vehicle vehicle = vehicleInfo.createVehicle();
        if(!vehicle.getLicensePlate().equals(currentLicensePlate)) {
            boolean isPresent = executor.execute(new CheckVehicleInDatabaseCommand(vehicle.getLicensePlate()));
            if (isPresent) {
                throw new SuchVehiclePresentException("Vehicle with " + vehicle.getLicensePlate() + " is already present in database");
            } else {
                executor.execute(new UpdateVehicleInTicketCommand(vehicle, currentLicensePlate));
            }
        }
        vehicleDAO.update(vehicle, currentLicensePlate);
        logger.info("Vehicle(" + currentLicensePlate+") was updated to "+vehicle);
        return vehicle;

    }
}