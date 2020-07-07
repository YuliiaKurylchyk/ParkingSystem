package com.kurylchyk.model.services.impl.vehicleCommand;

import com.kurylchyk.model.dao.VehicleDataUtil;
import com.kurylchyk.model.services.impl.Command;
import com.kurylchyk.model.dao.vehicles.VehicleDAO;
import com.kurylchyk.model.vehicles.Vehicle;

public class VehicleIsPresentCommand implements Command<Boolean> {

    private String licencePlate;

    public VehicleIsPresentCommand(String licencePlate){
        this.licencePlate = licencePlate;
    }

    @Override
    public Boolean execute() throws Exception {

       return VehicleDataUtil.isPresent(licencePlate);

    }
}
