package com.kurylchyk.model.services.impl.vehicleCommand;

import com.kurylchyk.model.dao.VehicleDAOFactory;
import com.kurylchyk.model.dao.VehicleDataUtil;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.services.impl.Command;
import com.kurylchyk.model.domain.vehicles.vehicleEnum.VehicleType;

public class CountPresentVehiclesCommand implements Command<Integer> {

    private VehicleType vehicleType;

    public CountPresentVehiclesCommand(){}

    public CountPresentVehiclesCommand(VehicleType vehicleType){
        this.vehicleType = vehicleType;
    }

    @Override
    public Integer execute() throws ParkingSystemException {

        if(vehicleType!=null){
            Integer count = VehicleDAOFactory.getVehicleDAO(vehicleType).countAllPresent();
            return count;
        }else {
            return VehicleDataUtil.countAllPresent();
        }
    }
}
