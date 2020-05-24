package com.kurylchyk.model.exceptions;

public class SuchVehiclePresentException extends  Exception {


    public SuchVehiclePresentException(String message) {
        super(message);
    }

    public SuchVehiclePresentException(String message,Throwable throwable) {
        super(message,throwable);
    }

    public String toString(){
        return super.getMessage();
    }

}
