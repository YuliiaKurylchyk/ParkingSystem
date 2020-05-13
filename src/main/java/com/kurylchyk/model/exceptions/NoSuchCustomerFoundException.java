package com.kurylchyk.model.exceptions;

public class NoSuchCustomerFoundException extends Exception{

    public NoSuchCustomerFoundException(String message) {
        super(message);
    }

    public NoSuchCustomerFoundException(String message,Throwable throwable) {
        super(message,throwable);
    }

    public String toString(){
        return super.getMessage();
    }

}
