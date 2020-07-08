package com.kurylchyk.model.services.impl.vehicleCommand;

import com.kurylchyk.model.dao.ParkingSlotDTO;
import com.kurylchyk.model.dao.VehicleDataUtil;
import com.kurylchyk.model.exceptions.NoSuchVehicleFoundException;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.services.impl.Command;
import com.kurylchyk.model.vehicles.VehicleType;

public class GetVehicleTypeCommand  implements Command<VehicleType> {


    private String licensePlate;

    public  GetVehicleTypeCommand(String licensePlate){
        this.licensePlate = licensePlate;
    }

    @Override
    public VehicleType execute() throws ParkingSystemException {
        return VehicleDataUtil.getType(licensePlate).orElseThrow(
                ()-> new NoSuchVehicleFoundException("No vehicle with license plate "+licensePlate + " was found"));
    }
}
