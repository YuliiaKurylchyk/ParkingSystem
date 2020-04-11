package com.kurylchyk.model.vehicles;

public class Car  extends  Vehicle{

    public Car(String sort, String model, String registrationNumber) {
        super(sort, model, registrationNumber);
    }

    @Override
    public boolean equals(Object anotherVehicle) {
        if (this == anotherVehicle) return true;
        if (anotherVehicle == null || getClass() != anotherVehicle.getClass()) return false;
        return  super.equals(anotherVehicle);
    }
}