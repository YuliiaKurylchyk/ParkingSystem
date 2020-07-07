package com.kurylchyk.model.services.impl.vehicleCommand;

import com.kurylchyk.model.dao.VehicleDAOFactory;
import com.kurylchyk.model.exceptions.SuchVehiclePresentException;
import com.kurylchyk.model.services.impl.Command;
import com.kurylchyk.model.dao.vehicles.VehicleDAO;
import com.kurylchyk.model.services.impl.CommandExecutor;
import com.kurylchyk.model.services.impl.ticketCommand.UpdateVehicleInTicketCommand;
import com.kurylchyk.model.services.impl.utilVehicle.VehicleInfo;
import com.kurylchyk.model.vehicles.Vehicle;

public class UpdateVehicleCommand implements Command<Vehicle> {

    private VehicleDAO vehicleDAO;
    private VehicleInfo vehicleInfo;
    private String currentLicensePlate;
    private CommandExecutor executor = new CommandExecutor();


    public UpdateVehicleCommand(VehicleInfo vehicleInfo, String licencePlate) {

        this.vehicleInfo = vehicleInfo;
        this.currentLicensePlate = licencePlate;
        vehicleDAO = VehicleDAOFactory.getVehicleDAO(vehicleInfo.getVehicleType());
    }

    @Override
    public Vehicle execute() throws Exception {

        Vehicle vehicle = vehicleInfo.createVehicle();
        if(!vehicle.getLicensePlate().equals(currentLicensePlate)) {
            boolean isPresent = executor.execute(new CheckVehicleInDatabase(vehicle.getLicensePlate()));
            if (isPresent) {
                throw new SuchVehiclePresentException("Vehicle with " + vehicle.getLicensePlate() + " is already present in database");
            } else {
                executor.execute(new UpdateVehicleInTicketCommand(vehicle, currentLicensePlate));
            }
        }
        vehicleDAO.update(vehicle, currentLicensePlate);
        return vehicle;

    }
}