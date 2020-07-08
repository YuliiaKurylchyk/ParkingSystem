package com.kurylchyk.model.exceptions;

public class NoSuchParkingTicketException extends ParkingSystemException {

    public NoSuchParkingTicketException(String message) {
        super(message);
    }



    public String toString(){
        return super.getMessage();
    }
}
