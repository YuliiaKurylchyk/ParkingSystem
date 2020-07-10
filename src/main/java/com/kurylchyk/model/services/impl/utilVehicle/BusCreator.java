package com.kurylchyk.model.services.impl.utilVehicle;

import com.kurylchyk.model.domain.vehicles.Bus;
import com.kurylchyk.model.domain.vehicles.vehicleEnum.VehicleType;

public class BusCreator extends VehicleCreator {

    private Integer countOfSeats;

    public BusCreator(String make, String model, String licensePlate, Integer countOfSeats){

        this.make = make;
        this.model = model;
        this.licensePlate = licensePlate;
        this.vehicleType = VehicleType.BUS;
        this.countOfSeats = countOfSeats;
    }

    @Override
    public Bus createVehicle() {
        return new Bus(make,model,licensePlate,countOfSeats);
    }
}
