package com.kurylchyk.model.services.impl.vehicleCommand;

import com.kurylchyk.model.services.impl.Command;
import com.kurylchyk.model.dao.VehicleDAO;
import com.kurylchyk.model.exceptions.NoSuchVehicleFoundException;
import com.kurylchyk.model.vehicles.Vehicle;

public class GetVehicleCommand implements Command<Vehicle> {
    private VehicleDAO vehicleDAO = new VehicleDAO();
    private String licencePlate;

    public GetVehicleCommand(String licencePlate) {
        this.licencePlate = licencePlate;
    }


    @Override
    public Vehicle execute() throws NoSuchVehicleFoundException {
        return vehicleDAO.select(licencePlate).orElseThrow(()->
                new NoSuchVehicleFoundException("No vehicle with " + licencePlate + " was found"));
    }
}
