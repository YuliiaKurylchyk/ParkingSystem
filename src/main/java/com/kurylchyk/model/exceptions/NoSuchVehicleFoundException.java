package com.kurylchyk.model.exceptions;

public class NoSuchVehicleFoundException  extends  Exception{


    public NoSuchVehicleFoundException(String message) {
        super(message);
    }

    public NoSuchVehicleFoundException(String message,Throwable throwable) {
        super(message,throwable);
    }

    public String toString(){
        return super.getMessage();
    }



}
