package com.kurylchyk.model.services.impl.vehicleCommand;

import com.kurylchyk.model.services.impl.Command;
import com.kurylchyk.model.dao.VehicleDAO;

public class VehicleStatusCommand implements Command<String> {

    private VehicleDAO vehicleDAO = new VehicleDAO();
    private String licencePlate;

    public VehicleStatusCommand(String licencePlate){
        this.licencePlate = licencePlate;
    }

    @Override
    public String execute() throws Exception {
        return vehicleDAO.getStatus(licencePlate);
    }
}
