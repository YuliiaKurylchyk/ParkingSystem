package com.kurylchyk.model.vehicles;

public class Truck extends Vehicle {
    private Boolean trailerPresent;

    public Truck(String sort, String model, String registrationNumber,Boolean trailerPresent) {
        super(sort, model, registrationNumber, VehicleType.TRUCK);
        this.trailerPresent = trailerPresent;
    }

    public Truck(String sort, String model, String registrationNumber) {
        super(sort, model, registrationNumber, VehicleType.TRUCK);

    }

    public Boolean getTrailerPresent() {
        return trailerPresent;
    }

    public void setTrailerPresent(Boolean trailerPresent) {
        this.trailerPresent = trailerPresent;
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
                "Trailer present : "+trailerPresent+"\n";

    }

}
