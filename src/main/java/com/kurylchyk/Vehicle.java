package com.kurylchyk;

public class Vehicle {
    private String model;
    private String sort;
    private String licencePlate;

    @Override
    public String toString(){
        return model +" "+sort+"-\t"+licencePlate;
    }

    @Override
    public boolean equals(Object anotherVehicle){

        return ((Vehicle) anotherVehicle).licencePlate==this.licencePlate
                && ((Vehicle)anotherVehicle).model==this.model
                && ((Vehicle)anotherVehicle).sort==this.sort;
    }

}