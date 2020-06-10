package com.kurylchyk.model.services.impl.vehicleCommand;

import com.kurylchyk.model.services.impl.Command;
import com.kurylchyk.model.dao.VehicleDAO;
import com.kurylchyk.model.vehicles.Vehicle;

public class UpdateVehicleCommand implements Command<Vehicle> {

    private VehicleDAO vehicleDAO = new VehicleDAO();
    private Vehicle vehicle;
    private  String licencePlate;

    public UpdateVehicleCommand(Vehicle vehicle,String licencePlate){

        this.vehicle = vehicle;
        this.licencePlate = licencePlate;
    }

    @Override
    public Vehicle execute() throws Exception {
        vehicleDAO.update(vehicle,licencePlate);
        return vehicle;
    }
}
