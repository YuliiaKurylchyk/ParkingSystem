package com.kurylchyk.model.exceptions;

public class NoAvailableParkingSlotException extends ParkingSystemException{

    public NoAvailableParkingSlotException(String message){
        super(message);
    }

    public String toString(){
        return super.getMessage();
    }
}
