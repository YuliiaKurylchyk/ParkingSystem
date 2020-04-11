package com.kurylchyk.controller;

import com.kurylchyk.model.ParkingPlace;
import com.kurylchyk.model.SizeOfSlot;
import com.kurylchyk.model.exceptions.NoAvailableSlot;
import com.kurylchyk.model.vehicles.Vehicle;
import com.sun.glass.ui.Size;

import java.util.LinkedList;


public class ParkingLot {

    @FunctionalInterface
    private interface Removal{
        void removeParkingPlace(LinkedList<ParkingPlace> fromList, int countOfRemoval);

    }

    @FunctionalInterface
    private interface  Adder{
        void addParkingPlace(LinkedList<ParkingPlace> toList, int countOfAdding,SizeOfSlot size, String PlaceID);

    }


    private static final int MAX_NUMBER_OF_MOTORBIKE_SLOT = 1;
    private static final int MAX_NUMBER_OF_CAR_SLOT = 5;
    private static final int MAX_NUMBER_OF_TRUCK_SLOT = 4;

    private LinkedList<ParkingPlace> forMotorbike;
    private LinkedList<ParkingPlace> forCar;
    private LinkedList<ParkingPlace> forTruck;

    private Removal removal = (from, countOfRemoval) -> {


        for(int index = 0; index<countOfRemoval; index++) {
            from.removeFirst();
        }
    };
    private Adder adder = (toList, countOfAdding, size, placeID) -> {


        for(int index =1; index<=countOfAdding; index++) {

            toList.add(new ParkingPlace(size,placeID+index));
        }

    };

    {
        forMotorbike = new LinkedList<>();
        forCar = new LinkedList<>();
        forTruck = new LinkedList<>();
    }


    {
        forMotorbike.add(new ParkingPlace(SizeOfSlot.SMALL,"S1"));

        forCar.add(new ParkingPlace(SizeOfSlot.MEDIUM,"M1"));
        forCar.add(new ParkingPlace(SizeOfSlot.MEDIUM,"M2"));
        forCar.add(new ParkingPlace(SizeOfSlot.MEDIUM,"M3"));
        forCar.add(new ParkingPlace(SizeOfSlot.MEDIUM,"M4"));
        forCar.add(new ParkingPlace(SizeOfSlot.MEDIUM,"M5"));

        forTruck.add(new ParkingPlace(SizeOfSlot.LARGE,"L1"));
        forTruck.add(new ParkingPlace(SizeOfSlot.LARGE,"L2"));
        forTruck.add(new ParkingPlace(SizeOfSlot.LARGE,"L3"));
        forTruck.add(new ParkingPlace(SizeOfSlot.LARGE,"L4"));


    }

    public ParkingPlace getParkingPlace(Vehicle vehicle) throws NoAvailableSlot{

        String typeOfVehicle = vehicle.getClass().getSimpleName();
        switch (typeOfVehicle) {
            case "Motorbike" :
              return  getMotorbikePlace();
            case "Car":
                return getCarPlace();
            case "Truck":
                return getTruckPlace();
        }
        //throw now such type of car
        return null;
    }

    private ParkingPlace getMotorbikePlace() throws NoAvailableSlot{
        if(hasAvailableMotorbikePlace()){
            return forMotorbike.removeFirst();
        } else {
            if(hasAvailableCarPlace()) {
                removal.removeParkingPlace(forCar,1);
                adder.addParkingPlace(forMotorbike,2,SizeOfSlot.SMALL,"Extra_M");

            } else if(hasAvailableTruckPlace()){
                removal.removeParkingPlace(forTruck,1);
                adder.addParkingPlace(forMotorbike,4, SizeOfSlot.SMALL,"Extra_L");
            }
            else{
                throw new NoAvailableSlot();
            }
        }
        return forMotorbike.removeFirst();
    };


   private boolean hasAvailableMotorbikePlace(){
        return  MAX_NUMBER_OF_MOTORBIKE_SLOT - forMotorbike.size()==0;
   }

    private boolean hasAvailableCarPlace(){
        return MAX_NUMBER_OF_CAR_SLOT - forCar.size()==0;
    }

    private boolean hasAvailableTruckPlace(){
        return MAX_NUMBER_OF_TRUCK_SLOT - forTruck.size()==0;
    }



    private ParkingPlace getCarPlace() throws NoAvailableSlot {

       if(hasAvailableCarPlace()) {
           return  forCar.removeFirst();
       } else {

           if(hasAvailableMotorbikePlace() && MAX_NUMBER_OF_MOTORBIKE_SLOT - forMotorbike.size() >=2) {
               removal.removeParkingPlace(forMotorbike,2);
               adder.addParkingPlace(forCar,1,SizeOfSlot.MEDIUM,"Extra_S");
           } else if(hasAvailableTruckPlace()){
               removal.removeParkingPlace(forTruck,1);//maybe too expensive.
               adder.addParkingPlace(forCar,2,SizeOfSlot.MEDIUM,"Extra_L");

           }
           else{
               throw new NoAvailableSlot();
           }
       }
       return  forCar.removeFirst();
    }


    private ParkingPlace getTruckPlace() throws NoAvailableSlot {

       if (hasAvailableTruckPlace()) {
           return forTruck.removeFirst();
       } else{

           if(hasAvailableMotorbikePlace() && MAX_NUMBER_OF_MOTORBIKE_SLOT - forMotorbike.size()>=4){
               removal.removeParkingPlace(forMotorbike,4);
               adder.addParkingPlace(forTruck,1,SizeOfSlot.LARGE,"Extra_S");
           }else if(hasAvailableCarPlace() & MAX_NUMBER_OF_CAR_SLOT-forCar.size()>=2) {
               removal.removeParkingPlace(forCar,2);
                adder.addParkingPlace(forTruck,1,SizeOfSlot.LARGE,"Extra_M");

           } else {
               throw new NoAvailableSlot();
           }

           return forTruck.removeFirst();
       }
    }
}
