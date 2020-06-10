package com.kurylchyk.model.services.impl.vehicleCommand;

import com.kurylchyk.model.services.impl.Command;
import com.kurylchyk.model.dao.VehicleDAO;
import com.kurylchyk.model.vehicles.Vehicle;

public class DeleteVehicleCommand implements Command<Void> {

    private VehicleDAO vehicleDAO = new VehicleDAO();
    private Vehicle vehicle;

    public DeleteVehicleCommand(Vehicle vehicle){
        this.vehicle = vehicle;
    }

    @Override
    public Void execute() throws Exception {
        vehicleDAO.delete(vehicle);
        return  null;
    }
}
