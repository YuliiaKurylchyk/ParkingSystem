package com.kurylchyk.model.dao;

import com.kurylchyk.model.dao.vehicles.*;
import com.kurylchyk.model.domain.vehicles.vehicleEnum.VehicleType;

public class VehicleDAOFactory {


    public static VehicleDAO getVehicleDAO(VehicleType vehicleType) {

        switch (vehicleType) {

            case MOTORBIKE: return new MotorbikeDAO();
            case CAR:return new CarDAO();
            case BUS: return new BusDAO();
            case TRUCK: return new TruckDAO();
        }
        return null;
    }

}
