package com.kurylchyk.model;

public enum TypeOfVehicle {

    LORRY(10.0),
    PASSANGER_CAR(5.0),
    TRUCK(12.0),
    BUS(10.0);

    private double price;

    TypeOfVehicle(double price) {

        this.price = price;
    }

    public double getPrice() {
        return price;
    }
}
