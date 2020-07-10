package com.kurylchyk.model.services.impl.utilVehicle;

import com.kurylchyk.model.domain.vehicles.Motorbike;
import com.kurylchyk.model.domain.vehicles.vehicleEnum.VehicleType;

public class MotorbikeCreator extends VehicleCreator {


    public MotorbikeCreator(String make, String model, String licensePlate){
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
