package com.kurylchyk.model.exceptions;

public class NoAvailableParkingPlaceException extends Exception{

    public NoAvailableParkingPlaceException(String message){
        super(message);
    }

    public String toString(){
        return super.getMessage();
    }
}
