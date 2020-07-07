package com.kurylchyk.model.services.impl.vehicleCommand;

import com.kurylchyk.model.dao.VehicleDAOFactory;
import com.kurylchyk.model.services.impl.Command;
import com.kurylchyk.model.dao.vehicles.VehicleDAO;
import com.kurylchyk.model.vehicles.Vehicle;

public class SaveVehicleCommand implements Command<Void> {
    private VehicleDAO vehicleDAO;
    private Vehicle vehicle;

    public SaveVehicleCommand(Vehicle vehicle){

        this.vehicle = vehicle;
        vehicleDAO = VehicleDAOFactory.getVehicleDAO(vehicle.getVehicleType());
    }

    @Override
    public Void execute() throws Exception {
        vehicleDAO.insert(vehicle);
        return  null;
    }
}
