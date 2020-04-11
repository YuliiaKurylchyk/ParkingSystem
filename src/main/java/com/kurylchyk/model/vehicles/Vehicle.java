package com.kurylchyk.model.vehicles;

public abstract class Vehicle {
    private String sort;
    private String model;
    private String registrationNumber;

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public Vehicle(String sort, String model, String registrationNumber) {
        this.sort = sort;
        this.model = model;
        this.registrationNumber = registrationNumber;
    }

    @Override
    public String toString() {
        return model + " " + sort + "-\t" + registrationNumber;
    }


    @Override
    public boolean equals(Object anotherVehicle) {

        return ((Vehicle) anotherVehicle).registrationNumber == this.registrationNumber
                && ((Vehicle) anotherVehicle).model == this.model
                && ((Vehicle) anotherVehicle).sort == this.sort;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + sort.hashCode();
        result = 31 * result + model.hashCode();
        result = 31 * result + registrationNumber.hashCode();
        return result;
    }

}