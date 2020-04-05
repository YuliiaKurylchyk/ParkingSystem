package com.kurylchyk.model;

public class Vehicle {
    private String sort;
    private String model;
    private String licencePlate;

    public String getLicencePlate() {
        return licencePlate;
    }

    public Vehicle(String sort, String model, String licencePlate){
        this.sort = sort;
        this.model = model;
        this.licencePlate = licencePlate;
    }

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