package com.kurylchyk.model.vehicles;

public abstract class Vehicle {

    protected String make;
    protected String model;
    protected  String licensePlate;
    protected TypeOfVehicle typeOfVehicle;

    public static VehicleBuilder newVehicle(){
        return new VehicleBuilder();
    }
    public void setMake(String make) {
        this.make = make;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setLicensePlate(String licencePlate) {
        this.licensePlate = licencePlate;
    }


    public Vehicle() {}
    public Vehicle(String make, String model, String licencePlate,TypeOfVehicle type) {
        this.make = make;
        this.model = model;
        this.licensePlate = licencePlate;
        this.typeOfVehicle = type;
    }

    @Override
    public String toString() {
        return  make + "\t" + model + "\t" + licensePlate;
    }

    public String getMake(){
        return  make;
    }

    public String getModel(){
        return model;
    }


    public String getLicensePlate() {
        return licensePlate;
    }
    public TypeOfVehicle getTypeOfVehicle(){
        return typeOfVehicle;
    }

    @Override
    public boolean equals(Object obj) {

        Vehicle anotherVehicle = (Vehicle) obj;
        return this.licensePlate.equals(anotherVehicle.licensePlate)
                && this.make.equals(anotherVehicle.make)
                && this.model.equals(anotherVehicle.model);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + make.hashCode();
        result = 31 * result + model.hashCode();
        result = 31 * result + licensePlate.hashCode();
        return result;
    }

}