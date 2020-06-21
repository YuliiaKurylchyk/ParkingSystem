package com.kurylchyk.model.services.impl.vehicleCommand;

import com.kurylchyk.model.services.impl.Command;
import com.kurylchyk.model.dao.VehicleDAO;
import com.kurylchyk.model.exceptions.NoSuchVehicleFoundException;
import com.kurylchyk.model.vehicles.Vehicle;

public class GetVehicleCommand implements Command<Vehicle> {
    private VehicleDAO vehicleDAO = new VehicleDAO();
    private String licensePlate;

    public GetVehicleCommand(String licensePlate) {
        this.licensePlate = licensePlate;
    }


    @Override
    public Vehicle execute() throws NoSuchVehicleFoundException {
        return vehicleDAO.select(licensePlate).orElseThrow(()->
                new NoSuchVehicleFoundException("No vehicle with " + licensePlate + " was found"));
    }
}
