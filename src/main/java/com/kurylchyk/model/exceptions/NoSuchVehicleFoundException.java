package com.kurylchyk.model.exceptions;

public class NoSuchVehicleFoundException  extends  ParkingSystemException{


    public NoSuchVehicleFoundException(String message) {
        super(message);
    }


    public String toString(){
        return super.getMessage();
    }



}
