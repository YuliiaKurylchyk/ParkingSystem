package com.kurylchyk.model.exceptions;

public class NoSuchParkingTicketException extends Exception {

    public NoSuchParkingTicketException(String message) {
        super(message);
    }

    public NoSuchParkingTicketException(String message,Throwable throwable) {
        super(message,throwable);
    }

    public String toString(){
        return super.getMessage();
    }
}
