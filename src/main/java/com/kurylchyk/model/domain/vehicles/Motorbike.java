package com.kurylchyk.model.domain.vehicles;


import com.kurylchyk.model.domain.vehicles.vehicleEnum.VehicleType;

public class Motorbike extends  Vehicle {

    public Motorbike(String sort, String model, String registrationNumber) {
        super(sort, model, registrationNumber, VehicleType.MOTORBIKE);
    }
    @Override
    public boolean equals(Object anotherVehicle) {
        if (this == anotherVehicle) return true;
        if (anotherVehicle == null || getClass() != anotherVehicle.getClass()) return false;
        return  super.equals(anotherVehicle);
    }
}
