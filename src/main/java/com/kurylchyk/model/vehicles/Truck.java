package com.kurylchyk.model.vehicles;

import java.util.Objects;

public class Truck extends Vehicle {

    //???? може ж мати онлі один причіп
    private int numberOfContainers;


    public Truck(String sort, String model, String registrationNumber,int numberOfContainers) {
        super(sort, model, registrationNumber);
        this.numberOfContainers = numberOfContainers;
    }


    public String toString(){
       return super.toString() + "\t" + numberOfContainers + "containers";
    }

    @Override
    public boolean equals(Object anotherVehicle) {
        if (this == anotherVehicle) return true;
        if (anotherVehicle == null || getClass() != anotherVehicle.getClass()) return false;
        if (!super.equals(anotherVehicle)) return false;
        Truck truck = (Truck) anotherVehicle;
        return numberOfContainers == truck.numberOfContainers;
    }

    @Override
    public int hashCode() {
        int result = 31* super.hashCode() + numberOfContainers;
        return  result;
    }
}
