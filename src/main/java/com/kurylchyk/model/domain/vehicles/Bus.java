package com.kurylchyk.model.domain.vehicles;

import com.kurylchyk.model.domain.vehicles.vehicleEnum.VehicleType;

public class Bus extends Vehicle{

    private Integer countOfSeats;

    public Integer getCountOfSeats() {
        return countOfSeats;
    }

    public void setCountOfSeats(Integer countOfSeats) {
        this.countOfSeats = countOfSeats;
    }

    public Bus(String sort, String model, String registrationNumber, Integer countOfSeats) {
        super(sort, model, registrationNumber, VehicleType.BUS);
        this.countOfSeats = countOfSeats;
    }

    public Bus(String sort, String model, String registrationNumber) {
        super(sort, model, registrationNumber, VehicleType.BUS);
    }


    @Override
    public boolean equals(Object anotherVehicle) {
        if (this == anotherVehicle) return true;
        if (anotherVehicle == null || getClass() != anotherVehicle.getClass()) return false;
        return  super.equals(anotherVehicle) && ((Bus)anotherVehicle).countOfSeats==countOfSeats;
    }

    @Override
    public String toString(){
      return  super.toString() + "\n"+
                "Count of seats : "+ countOfSeats+"\n";

    }
}
