package com.kurylchyk.model.vehicles;

import java.util.Objects;

public class Truck extends Vehicle {


    public Truck(String sort, String model, String registrationNumber) {
        super(sort, model, registrationNumber,TypeOfVehicle.TRUCK);
    }


    public Truck(){
        typeOfVehicle = TypeOfVehicle.TRUCK;

    }
    @Override
    public boolean equals(Object anotherVehicle) {
        if (this == anotherVehicle) return true;
        if (anotherVehicle == null || getClass() != anotherVehicle.getClass()) return false;
        return  super.equals(anotherVehicle);
    }

}
