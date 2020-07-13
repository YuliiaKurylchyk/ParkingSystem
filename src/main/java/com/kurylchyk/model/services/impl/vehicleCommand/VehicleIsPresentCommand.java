package com.kurylchyk.model.services.impl.vehicleCommand;

import com.kurylchyk.model.dao.VehicleDataUtil;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.services.impl.Command;

/**
 * Checks if there is information about vehicle in db
 */
public class VehicleIsPresentCommand implements Command<Boolean> {

    private String licencePlate;
    private VehicleDataUtil vehicleDataUtil = new VehicleDataUtil();
    public VehicleIsPresentCommand(String licencePlate){

        this.licencePlate = licencePlate;
    }

    VehicleIsPresentCommand(String licencePlate,VehicleDataUtil vehicleDataUtil){

        this.licencePlate = licencePlate;
        this.vehicleDataUtil = vehicleDataUtil;
    }
    @Override
    public Boolean execute() throws ParkingSystemException {

       return vehicleDataUtil.isPresent(licencePlate);

    }
}
