package com.kurylchyk.model.services.impl.vehicleCommand;

import com.kurylchyk.model.dao.VehicleDataUtil;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.exceptions.SuchVehiclePresentException;
import com.kurylchyk.model.services.impl.Command;
import com.kurylchyk.model.parkingTicket.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class CheckVehicleInDatabaseCommand implements Command<Boolean> {


    private String licensePlate;
    private static final Logger logger = LogManager.getLogger(CheckVehicleInDatabaseCommand.class);

    public CheckVehicleInDatabaseCommand(String licensePlate) {
        this.licensePlate  = licensePlate;
    }

    @Override
    public Boolean execute() throws ParkingSystemException {

        logger.debug("Checking whether vehicle is in database");
        if (VehicleDataUtil.isPresent(licensePlate)) {
            System.out.println("is present = true");
            if (VehicleDataUtil.getStatus(licensePlate).equals(Status.PRESENT)) {
                System.out.println("is really present");
                throw new SuchVehiclePresentException("Such vehicle with licence plate "
                        + licensePlate + " is already present");
            } else {
                return true;
            }
        }
        return false;
    }

}
