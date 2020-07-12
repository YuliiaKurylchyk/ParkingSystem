package com.kurylchyk.model.services.impl.vehicleCommand;

import com.kurylchyk.model.dao.VehicleDataUtil;
import com.kurylchyk.model.exceptions.NoSuchVehicleFoundException;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.services.impl.Command;
import com.kurylchyk.model.domain.vehicles.vehicleEnum.VehicleType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetVehicleTypeCommand  implements Command<VehicleType> {


    private String licensePlate;
    private VehicleDataUtil vehicleDataUtil =  new VehicleDataUtil();
    private static final Logger logger = LogManager.getLogger(GetVehicleCommand.class);

    public  GetVehicleTypeCommand(String licensePlate){
        this.licensePlate = licensePlate;
    }


    GetVehicleTypeCommand(String licensePlate,VehicleDataUtil vehicleDataUtil){

        this.licensePlate = licensePlate;
        this.vehicleDataUtil = vehicleDataUtil;
    }

    @Override
    public VehicleType execute() throws ParkingSystemException {
        logger.debug("Getting vehicle type by license plate");
        return vehicleDataUtil.getType(licensePlate).orElseThrow(
                ()-> new NoSuchVehicleFoundException("No vehicle with license plate "+licensePlate + " was found"));
    }
}
