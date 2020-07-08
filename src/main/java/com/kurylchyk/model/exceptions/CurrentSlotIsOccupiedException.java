package com.kurylchyk.model.exceptions;


public class CurrentSlotIsOccupiedException extends ParkingSystemException{


    public CurrentSlotIsOccupiedException(String message){
        super(message);
    }

    public String toString(){
        return super.getMessage();
    }
}
