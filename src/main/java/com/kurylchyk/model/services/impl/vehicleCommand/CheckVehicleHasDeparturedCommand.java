package com.kurylchyk.model.services.impl.vehicleCommand;

import com.kurylchyk.model.dao.VehicleDataUtil;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.exceptions.SuchVehiclePresentException;
import com.kurylchyk.model.services.impl.Command;
import com.kurylchyk.model.domain.parkingTicket.ticketEnum.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class CheckVehicleHasDeparturedCommand implements Command<Boolean> {


    private String licensePlate;
    private VehicleDataUtil vehicleDataUtil = new VehicleDataUtil();
    private static final Logger logger = LogManager.getLogger(CheckVehicleHasDeparturedCommand.class);

    public CheckVehicleHasDeparturedCommand(String licensePlate) {
        this.licensePlate = licensePlate;
    }


    CheckVehicleHasDeparturedCommand(String licensePlate,VehicleDataUtil vehicleDataUtil) {
        this.licensePlate = licensePlate;
        this.vehicleDataUtil = vehicleDataUtil;
    }


    @Override
    public Boolean execute() throws ParkingSystemException {

        logger.debug("Checking vehicle status in database");

        if (vehicleDataUtil.getStatus(licensePlate).equals(Status.PRESENT)) {
            throw new SuchVehiclePresentException("Such vehicle with license plate "
                    + licensePlate + " is already present");
        }
        return true;
    }

}
