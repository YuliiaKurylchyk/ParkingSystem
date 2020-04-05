package com.kurylchyk.model;

public class Payment {

    private static double amount;

    public static double calculatePrice(int hours,TypeOfVehicle typeOfVehicle){
        return hours* typeOfVehicle.getPrice();
    }
}
