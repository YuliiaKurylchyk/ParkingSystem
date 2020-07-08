package com.kurylchyk.controller;

import com.kurylchyk.model.services.impl.utilVehicle.*;
import com.kurylchyk.model.vehicles.CarSize;
import com.kurylchyk.model.vehicles.VehicleType;

import javax.servlet.http.HttpServletRequest;

public class VehicleInfoFactory {

    public static VehicleInfo getVehicleInformation(HttpServletRequest req){
        String make = req.getParameter("make");
        String model = req.getParameter("model");
        String licensePlate = req.getParameter("licensePlate");
        VehicleType vehicleType = VehicleType.valueOf(req.getParameter("typeOfVehicle"));
        VehicleInfo vehicleInfo = null;

        switch (vehicleType) {
            case MOTORBIKE:
                vehicleInfo = new MotorbikeInfo(make, model, licensePlate);
                break;
            case CAR:
                vehicleInfo = new CarInfo(make, model, licensePlate, CarSize.valueOf(req.getParameter("carSize")));
                break;
            case TRUCK:
                vehicleInfo = new TruckInfo(make, model, licensePlate, Boolean.parseBoolean(req.getParameter("trailerPresent")));
                break;
            case BUS:
                vehicleInfo = new BusInfo(make, model, licensePlate, Integer.parseInt(req.getParameter("countOfSeats")));
        }

        return vehicleInfo;
    }
}
