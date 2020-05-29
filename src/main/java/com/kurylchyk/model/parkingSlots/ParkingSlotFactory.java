package com.kurylchyk.model.parkingSlots;


import com.kurylchyk.model.vehicles.TypeOfVehicle;
import com.sun.glass.ui.Size;

public class ParkingSlotFactory {

    public ParkingSlot getParkingSlot(SizeOfSlot size){
        ParkingSlot parkingSlot = null;

        switch (size) {
            case SMALL:
                parkingSlot  = new SmallSlot();
                break;
            case MEDIUM:
                parkingSlot =  new MediumSlot();
                break;
            case LARGE:
                parkingSlot = new LargeSlot();
                break;
        }
        return  parkingSlot;
    }

    public ParkingSlot getParkingSlot(TypeOfVehicle typeOfVehicle){
        SizeOfSlot sizeOfSlot = null;

        switch (typeOfVehicle){
            case MOTORBIKE:
                sizeOfSlot = SizeOfSlot.SMALL;
                break;
            case CAR:
                sizeOfSlot = SizeOfSlot.MEDIUM;
                break;
            case TRUCK:
            case BUS:
                sizeOfSlot =SizeOfSlot.LARGE;
                break;
        }
        return  getParkingSlot(sizeOfSlot);
    }


}
