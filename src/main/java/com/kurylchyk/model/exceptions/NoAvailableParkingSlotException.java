package com.kurylchyk.model.exceptions;

public class NoAvailableParkingSlotException extends Exception{

    public NoAvailableParkingSlotException(String message){
        super(message);
    }

    public String toString(){
        return super.getMessage();
    }
}
