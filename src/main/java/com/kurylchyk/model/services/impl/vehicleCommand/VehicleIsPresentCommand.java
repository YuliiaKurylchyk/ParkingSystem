package com.kurylchyk.model.services.impl.vehicleCommand;

import com.kurylchyk.model.services.impl.Command;
import com.kurylchyk.model.dao.VehicleDAO;

public class VehicleIsPresentCommand implements Command<Boolean> {

    private VehicleDAO vehicleDAO = new VehicleDAO();
    private String licencePlate;

    public VehicleIsPresentCommand(String licencePlate){
        this.licencePlate = licencePlate;
    }

    @Override
    public Boolean execute() throws Exception {
       return vehicleDAO.isPresent(licencePlate);
    }
}
