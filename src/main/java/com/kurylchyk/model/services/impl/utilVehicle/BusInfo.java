package com.kurylchyk.model.services.impl.utilVehicle;

import com.kurylchyk.model.vehicles.Bus;
import com.kurylchyk.model.vehicles.VehicleType;

public class BusInfo  extends VehicleInfo{

    private Integer countOfSeats;

    public BusInfo(String make,String model, String licensePlate, Integer countOfSeats){

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
