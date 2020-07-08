package com.kurylchyk.model.services.impl.vehicleCommand;

import com.kurylchyk.model.dao.VehicleDataUtil;
import com.kurylchyk.model.dao.vehicles.VehicleDAO;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.exceptions.SuchVehiclePresentException;
import com.kurylchyk.model.services.impl.Command;
import com.kurylchyk.model.vehicles.Vehicle;
import com.kurylchyk.model.parkingTicket.Status;

public class CheckVehicleInDatabaseCommand implements Command<Boolean> {


    private String licensePlate;

    public CheckVehicleInDatabaseCommand(String licensePlate) {
        this.licensePlate  = licensePlate;

    }

    @Override
    public Boolean execute() throws ParkingSystemException {

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
