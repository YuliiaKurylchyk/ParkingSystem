package com.kurylchyk.model;

import com.kurylchyk.model.parkingSlots.*;
import com.kurylchyk.model.exceptions.NoAvailableParkingPlaceException;
import com.kurylchyk.model.exceptions.NoSuchTypeOfVehicleException;
import com.kurylchyk.model.vehicles.Vehicle;

import java.util.LinkedList;


public class ParkingLot {

    @FunctionalInterface
    private interface Removal{
        void removeParkingPlace(LinkedList<? extends ParkingSlot> fromList, int countOfRemoval);

    }

    @FunctionalInterface
    private interface  Adder<T extends ParkingSlot>{
        void addParkingPlace(LinkedList<T> toList,
                             T parkingSlot ,
                             int countOfAdding);

    }
    //we cant use wildcard here because it is not allowed to set element while "? extends SomeClass"


    private static final int MAX_NUMBER_OF_MOTORBIKE_SLOT = 1;
    private static final int MAX_NUMBER_OF_CAR_SLOT = 5;
    private static final int MAX_NUMBER_OF_TRUCK_SLOT = 4;

    private LinkedList<SmallSlot> smallSlots;
    private LinkedList<MediumSlot> mediumSlots;
    private LinkedList<LargeSlot> largeSlots;

    private Removal removal = (from, countOfRemoval) -> {
        for(int index = 0; index<countOfRemoval; index++) {
            from.removeFirst();
        }
    };

    private Adder adder = (toList,parkingSlot,countOfAdding) -> {

        for(int index =1; index<=countOfAdding; index++) {

            toList.add(parkingSlot);
        }
    };

    {
        smallSlots = new LinkedList<>();
        mediumSlots = new LinkedList<>();
        largeSlots = new LinkedList<>();
    }


    {
        smallSlots.add(new SmallSlot());

        mediumSlots.add(new MediumSlot());
        mediumSlots.add(new MediumSlot());
        mediumSlots.add(new MediumSlot());
        mediumSlots.add(new MediumSlot());
        mediumSlots.add(new MediumSlot());


        largeSlots.add(new LargeSlot());
        largeSlots.add(new LargeSlot());
        largeSlots.add(new LargeSlot());
        largeSlots.add(new LargeSlot());
    }

    public ParkingSlot getParkingPlace(Vehicle vehicle) throws NoAvailableParkingPlaceException, NoSuchTypeOfVehicleException {

        switch (vehicle.getTypeOfVehicle()) {
            case MOTORBIKE:
              return  getSmallSlot();
            case CAR:
                return getMediumSlot();
            case TRUCK:
            case BUS:
               return getLargeSlot();
            default:
                throw new NoSuchTypeOfVehicleException("There is no vehicle type like ");
                //what now should I do with it??
        }
    }


    public void returnParkingPlaceBack(ParkingSlot parkingSlot){

        switch (parkingSlot.getSizeOfSlot()){
            case SMALL:
                smallSlots.add((SmallSlot) parkingSlot);
                break;
            case MEDIUM:
                mediumSlots.add((MediumSlot) parkingSlot);
                break;
            case LARGE:
                largeSlots.add((LargeSlot)parkingSlot);
                break;
        }
    }

    private ParkingSlot getSmallSlot() throws NoAvailableParkingPlaceException {
        if(hasAvailableMotorbikePlace()){
            return smallSlots.removeFirst();
        } else {
            if(hasAvailableCarPlace()) {
                removal.removeParkingPlace(mediumSlots,1);
                adder.addParkingPlace(smallSlots,new SmallSlot(),2);

            } else if(hasAvailableTruckPlace()){
                removal.removeParkingPlace(largeSlots,1);
                adder.addParkingPlace(smallSlots,new SmallSlot(),4);
            }
            else{
                throw new NoAvailableParkingPlaceException("Sorry there is no available slot for you :()");
            }
        }
        return smallSlots.removeFirst();
    };


   private boolean hasAvailableMotorbikePlace(){
        return  MAX_NUMBER_OF_MOTORBIKE_SLOT - smallSlots.size()>=0;
   }

    private boolean hasAvailableCarPlace(){
        return MAX_NUMBER_OF_CAR_SLOT - mediumSlots.size()>=0;
    }

    private boolean hasAvailableTruckPlace(){
        return MAX_NUMBER_OF_TRUCK_SLOT - largeSlots.size()>=0;
    }

    private ParkingSlot getMediumSlot() throws NoAvailableParkingPlaceException {

       if(hasAvailableCarPlace()) {
           return  mediumSlots.removeFirst();
       } else {

           if(hasAvailableMotorbikePlace() && MAX_NUMBER_OF_MOTORBIKE_SLOT - smallSlots.size() >=2) {
               removal.removeParkingPlace(smallSlots,2);
               adder.addParkingPlace(mediumSlots,new MediumSlot(),1);
           } else if(hasAvailableTruckPlace()){
               removal.removeParkingPlace(largeSlots,1);//maybe too expensive.
               adder.addParkingPlace(mediumSlots,new MediumSlot(),2);

           }
           else{
               throw new NoAvailableParkingPlaceException("Sorry there is no available slot for you :(");
           }
       }
       return  mediumSlots.removeFirst();
    }

    private ParkingSlot getLargeSlot() throws NoAvailableParkingPlaceException {

       if (hasAvailableTruckPlace()) {
           return largeSlots.removeFirst();
       } else{

           if(hasAvailableMotorbikePlace() && MAX_NUMBER_OF_MOTORBIKE_SLOT - smallSlots.size()>=4){
               removal.removeParkingPlace(smallSlots,4);
               adder.addParkingPlace(largeSlots,new LargeSlot(),1);
           }else if(hasAvailableCarPlace() & MAX_NUMBER_OF_CAR_SLOT-mediumSlots.size()>=2) {
               removal.removeParkingPlace(mediumSlots,2);
                adder.addParkingPlace(largeSlots,new LargeSlot(),1);

           } else {
               throw new NoAvailableParkingPlaceException("Sorry there is no available slot for you :(");
           }

           return largeSlots.removeFirst();
       }
    }
}
