package com.kurylchyk.model.services.impl.vehicleCommand;

import com.kurylchyk.model.dao.ParkingSlotDTO;
import com.kurylchyk.model.dao.VehicleDataUtil;
import com.kurylchyk.model.exceptions.NoSuchVehicleFoundException;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.services.impl.Command;
import com.kurylchyk.model.vehicles.VehicleType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetVehicleTypeCommand  implements Command<VehicleType> {


    private String licensePlate;
    private static final Logger logger = LogManager.getLogger(GetVehicleCommand.class);

    public  GetVehicleTypeCommand(String licensePlate){
        this.licensePlate = licensePlate;
    }

    @Override
    public VehicleType execute() throws ParkingSystemException {
        logger.debug("Getting vehicle type by license plate");
        return VehicleDataUtil.getType(licensePlate).orElseThrow(
                ()-> new NoSuchVehicleFoundException("No vehicle with license plate "+licensePlate + " was found"));
    }
}
