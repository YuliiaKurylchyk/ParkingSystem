package com.kurylchyk.model.vehicles;

public class Car  extends  Vehicle{

    private CarSize carSize;

    public CarSize getCarSize() {
        return carSize;
    }

    public void setCarSize(CarSize carSize) {
        this.carSize = carSize;
    }

    public Car(String make, String model, String registrationNumber) {
        super(make, model, registrationNumber, VehicleType.CAR);
    }
    public Car(String make, String model, String registrationNumber,CarSize carSize) {
        super(make, model, registrationNumber, VehicleType.CAR);
        this.carSize = carSize;
    }


    @Override
    public boolean equals(Object anotherVehicle) {
        if (this == anotherVehicle) return true;
        if (anotherVehicle == null || getClass() != anotherVehicle.getClass()) return false;
        return  super.equals(anotherVehicle);
    }


    @Override
    public String toString(){
        return  super.toString() + "\n"+
                "Car size : "+carSize+"\n";

    }
}
