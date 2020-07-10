package com.kurylchyk.controller;

import com.kurylchyk.model.services.impl.utilVehicle.*;
import com.kurylchyk.model.domain.vehicles.vehicleEnum.CarSize;
import com.kurylchyk.model.domain.vehicles.vehicleEnum.VehicleType;

import javax.servlet.http.HttpServletRequest;

public class VehicleCreatorFactory {

    public static VehicleCreator getVehicleInformation(HttpServletRequest req){
        String make = req.getParameter("make");
        String model = req.getParameter("model");
        String licensePlate = req.getParameter("licensePlate");
        VehicleType vehicleType = VehicleType.valueOf(req.getParameter("typeOfVehicle"));
        VehicleCreator vehicleInfo = null;

        switch (vehicleType) {
            case MOTORBIKE:
                vehicleInfo = new MotorbikeCreator(make, model, licensePlate);
                break;
            case CAR:
                vehicleInfo = new CarCreator(make, model, licensePlate, CarSize.valueOf(req.getParameter("carSize")));
                break;
            case TRUCK:
                vehicleInfo = new TruckCreator(make, model, licensePlate, Boolean.parseBoolean(req.getParameter("trailerPresent")));
                break;
            case BUS:
                vehicleInfo = new BusCreator(make, model, licensePlate, Integer.parseInt(req.getParameter("countOfSeats")));
        }

        return vehicleInfo;
    }
}
