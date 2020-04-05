package com.kurylchyk.model;

public class ParkingPlace {
    private int placeNumber;
    private boolean available;


    public ParkingPlace(int placeNumber) {
        this.placeNumber = placeNumber;
    }

    public int getPlaceNumber() {
        return placeNumber;
    }
}
