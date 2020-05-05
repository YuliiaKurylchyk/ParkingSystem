package com.kurylchyk.model.parkingSlots;

public abstract class ParkingSlot {

    protected SizeOfSlot sizeOfSlot;
    protected final Integer parkingSlotID;


    public ParkingSlot(Integer parkingSlotID,SizeOfSlot sizeOfSlot) {
        this.parkingSlotID  = parkingSlotID;
        this.sizeOfSlot = sizeOfSlot;

    }

    public void setSizeOfSlot(SizeOfSlot sizeOfSlot) {
        this.sizeOfSlot = sizeOfSlot;
    }

    public SizeOfSlot getSizeOfSlot(){
        return sizeOfSlot;
    }

    public Integer getParkingSlotID() {
        return parkingSlotID;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31*result + sizeOfSlot.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {

        ParkingSlot anotherParkingSlot = (ParkingSlot) obj;
        return  this.sizeOfSlot.equals(anotherParkingSlot.sizeOfSlot);
    }

    @Override
    public String toString() {
        return getSizeOfSlot().toString();
    }
}
