package com.kurylchyk.model.services.impl.vehicleCommand;

import com.kurylchyk.model.dao.VehicleDAOFactory;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.services.impl.Command;
import com.kurylchyk.model.dao.vehicles.VehicleDAO;
import com.kurylchyk.model.vehicles.Vehicle;

public class DeleteVehicleCommand implements Command<Void> {

    private VehicleDAO vehicleDAO;
    private Vehicle vehicle;

    public DeleteVehicleCommand(Vehicle vehicle){

        this.vehicle = vehicle;
        vehicleDAO = VehicleDAOFactory.getVehicleDAO(vehicle.getVehicleType());
    }

    @Override
    public Void execute() throws ParkingSystemException {
        vehicleDAO.delete(vehicle);
        return  null;
    }
}
