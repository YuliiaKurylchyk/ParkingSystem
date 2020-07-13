package com.kurylchyk.model.services.impl.vehicleCommand;

import com.kurylchyk.model.dao.VehicleDAOFactory;
import com.kurylchyk.model.dao.VehicleDataUtil;
import com.kurylchyk.model.dao.vehicles.VehicleDAO;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.services.impl.Command;
import com.kurylchyk.model.domain.vehicles.vehicleEnum.VehicleType;

public class CountPresentVehiclesCommand implements Command<Integer> {

    private VehicleType vehicleType;
    private VehicleDataUtil vehicleDataUtil;
    private VehicleDAO vehicleDAO;

    public CountPresentVehiclesCommand(){
        vehicleDataUtil = new VehicleDataUtil();
    }

    public CountPresentVehiclesCommand(VehicleType vehicleType){

        this.vehicleType = vehicleType;
        vehicleDAO = VehicleDAOFactory.getVehicleDAO(vehicleType);
    }

    CountPresentVehiclesCommand(VehicleType vehicleType,VehicleDAO vehicleDAO,VehicleDataUtil vehicleDataUtil){

        this.vehicleType = vehicleType;
        this.vehicleDAO = vehicleDAO;
        this.vehicleDataUtil = vehicleDataUtil;
    }




    @Override
    public Integer execute() throws ParkingSystemException {

        if(vehicleType!=null){
            Integer count = vehicleDAO.countAllPresent();
            return count;
        }else {
            return vehicleDataUtil.countAllPresent();
        }
    }
}
