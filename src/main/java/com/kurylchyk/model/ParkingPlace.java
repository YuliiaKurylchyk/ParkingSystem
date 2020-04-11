package com.kurylchyk.model;

import com.sun.glass.ui.Size;

public class ParkingPlace {

    private SizeOfSlot sizeOfSlot;
    private String slotID;

    public ParkingPlace(SizeOfSlot sizeOfSlot, String slotID) {
        this.sizeOfSlot = sizeOfSlot;
        this.slotID = slotID;

    }

    public SizeOfSlot getSizeOfSlot(){
        return sizeOfSlot;
    }
    public String getSlotID() {
        return slotID;
    }
}
