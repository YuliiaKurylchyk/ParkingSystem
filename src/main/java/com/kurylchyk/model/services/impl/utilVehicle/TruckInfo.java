package com.kurylchyk.model.services.impl.utilVehicle;

import com.kurylchyk.model.vehicles.Truck;
import com.kurylchyk.model.vehicles.VehicleType;

public class TruckInfo extends VehicleInfo {

    private Boolean trailerPresent;

    public TruckInfo(String make,String model, String licensePlate, Boolean trailerPresent){

        this.make = make;
        this.model = model;
        this.licensePlate = licensePlate;
        this.trailerPresent = trailerPresent;
        this.vehicleType = VehicleType.TRUCK;
    }

    @Override
    public Truck createVehicle() {
        return new Truck(make,model,licensePlate,trailerPresent);
    }
}
