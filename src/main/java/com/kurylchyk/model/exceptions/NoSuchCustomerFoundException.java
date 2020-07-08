package com.kurylchyk.model.exceptions;

public class NoSuchCustomerFoundException extends ParkingSystemException{

    public NoSuchCustomerFoundException(String message) {
        super(message);
    }


    public String toString(){
        return super.getMessage();
    }

}
