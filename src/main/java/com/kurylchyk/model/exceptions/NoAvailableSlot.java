package com.kurylchyk.model.exceptions;

public class NoAvailableSlot extends Exception{

    @Override
    public String toString() {
        return "Sorry there is no available slot for you :(";
    }
}
