package com.kurylchyk.model.parkingSlots;

public abstract class ParkingSlot {

    protected SizeOfSlot sizeOfSlot;
    protected String slotID;
    protected String codeOfParkingSlot;
    public ParkingSlot(SizeOfSlot sizeOfSlot, String slotID) {
        this.sizeOfSlot = sizeOfSlot;
        this.slotID = slotID;

    }

    public SizeOfSlot getSizeOfSlot(){
        return sizeOfSlot;
    }
    public String getSlotID() {
        return slotID;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31*result + sizeOfSlot.hashCode();
        result = 31* result + slotID.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {

        ParkingSlot anotherParkingSlot = (ParkingSlot) obj;
        return  this.sizeOfSlot.equals(anotherParkingSlot.sizeOfSlot)
                && this.slotID.equals(anotherParkingSlot.slotID);
    }

    @Override
    public String toString() {
        return getSlotID();
    }
}
