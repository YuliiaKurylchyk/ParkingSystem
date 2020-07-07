package com.kurylchyk.model.services.impl.utilVehicle;

import com.kurylchyk.model.vehicles.Motorbike;
import com.kurylchyk.model.vehicles.VehicleType;

public class MotorbikeInfo extends VehicleInfo{


    public MotorbikeInfo(String make,String model,String licensePlate){
        this.make = make;
        this.model = model;
        this.licensePlate = licensePlate;
        this.vehicleType = VehicleType.MOTORBIKE;
    }

    @Override
    public Motorbike createVehicle() {
        return new Motorbike(make,model,licensePlate);
    }
}
