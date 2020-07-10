package com.kurylchyk.model.services.impl.utilVehicle;

import com.kurylchyk.model.domain.vehicles.Truck;
import com.kurylchyk.model.domain.vehicles.vehicleEnum.VehicleType;

public class TruckCreator extends VehicleCreator {

    private Boolean trailerPresent;

    public TruckCreator(String make, String model, String licensePlate, Boolean trailerPresent){

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
