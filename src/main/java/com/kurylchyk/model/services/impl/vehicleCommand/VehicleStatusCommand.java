package com.kurylchyk.model.services.impl.vehicleCommand;

import com.kurylchyk.model.dao.VehicleDataUtil;
import com.kurylchyk.model.parkingTicket.Status;
import com.kurylchyk.model.services.impl.Command;
import com.kurylchyk.model.dao.vehicles.VehicleDAO;
import com.kurylchyk.model.vehicles.Vehicle;

public class VehicleStatusCommand implements Command<Status> {

    private String licencePlate;

    public VehicleStatusCommand(String licencePlate){
        this.licencePlate = licencePlate;
    }

    @Override
    public Status execute() throws Exception {
        return VehicleDataUtil.getStatus(licencePlate);

    }
}
