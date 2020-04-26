package com.kurylchyk.model.vehicles;

public class Bus extends Vehicle{

    public Bus(String sort, String model, String registrationNumber) {
        super(sort, model, registrationNumber,TypeOfVehicle.BUS);
    }



    @Override
    public boolean equals(Object anotherVehicle) {
        if (this == anotherVehicle) return true;
        if (anotherVehicle == null || getClass() != anotherVehicle.getClass()) return false;
        return  super.equals(anotherVehicle);
    }
}
