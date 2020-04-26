package com.kurylchyk.model.exceptions;

import java.util.NoSuchElementException;

//add javadoc comments
public class NoSuchTypeOfVehicleException extends Exception{

    public NoSuchTypeOfVehicleException(String message) {
        super(message);
    }

    public NoSuchTypeOfVehicleException(String message,Throwable throwable) {
        super(message,throwable);
    }

    public String toString(){
        return super.getMessage();
    }

}
