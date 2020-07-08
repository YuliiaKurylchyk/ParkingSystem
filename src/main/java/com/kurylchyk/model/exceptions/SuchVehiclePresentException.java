package com.kurylchyk.model.exceptions;

public class SuchVehiclePresentException extends ParkingSystemException {


    public SuchVehiclePresentException(String message) {
        super(message);
    }

    public String toString() {
        return super.getMessage();
    }

}
