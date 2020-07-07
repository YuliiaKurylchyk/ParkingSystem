package com.kurylchyk.model.exceptions;

import java.util.concurrent.ExecutorService;

public class CurrentSlotIsOccupiedException extends Exception {

    public CurrentSlotIsOccupiedException(String message){
        super(message);
    }

    public String toString(){
        return super.getMessage();
    }
}
