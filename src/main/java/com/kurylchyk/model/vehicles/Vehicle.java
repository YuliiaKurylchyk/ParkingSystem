package com.kurylchyk.model.vehicles;

public abstract class Vehicle {
    //maybe you should to make these protected
    private String make;
    private String model;
    private String licencePlate;
    private TypeOfVehicle typeOfVehicle;

    public void setMake(String make) {
        this.make = make;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setLicencePlate(String licencePlate) {
        this.licencePlate = licencePlate;
    }

    public void setTypeOfVehicle(TypeOfVehicle type) {
        this.typeOfVehicle = type;
    }

    public Vehicle() {}
    public Vehicle(String make, String model, String licencePlate,TypeOfVehicle type) {
        this.make = make;
        this.model = model;
        this.licencePlate = licencePlate;
        this.typeOfVehicle = type;
    }

    @Override
    public String toString() {
        return make + "\t" + model + "\t" + licencePlate;
    }

    public String getMake(){
        return  make;
    }

    public String getModel(){
        return model;
    }


    public String getLicencePlate() {
        return licencePlate;
    }
    public TypeOfVehicle getTypeOfVehicle(){
        return typeOfVehicle;
    }

    @Override
    public boolean equals(Object obj) {

        Vehicle anotherVehicle = (Vehicle) obj;
        return this.licencePlate.equals(anotherVehicle.licencePlate)
                && this.make.equals(anotherVehicle.make)
                && this.model.equals(anotherVehicle.model);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + make.hashCode();
        result = 31 * result + model.hashCode();
        result = 31 * result + licencePlate.hashCode();
        return result;
    }

}