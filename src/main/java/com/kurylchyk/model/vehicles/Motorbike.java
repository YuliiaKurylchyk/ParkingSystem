package com.kurylchyk.model.vehicles;


public class Motorbike extends  Vehicle {

    public Motorbike(String sort, String model, String registrationNumber) {
        super(sort, model, registrationNumber, TypeOfVehicle.MOTORBIKE);
    }

    public Motorbike(){}
    @Override
    public boolean equals(Object anotherVehicle) {
        if (this == anotherVehicle) return true;
        if (anotherVehicle == null || getClass() != anotherVehicle.getClass()) return false;
        return  super.equals(anotherVehicle);
    }
}
