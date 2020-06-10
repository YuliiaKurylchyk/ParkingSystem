package com.kurylchyk.model.parkingSlots;

public abstract class ParkingSlot {

    protected final SizeOfSlot sizeOfSlot;
    protected Integer parkingSlotID;
    protected  Integer pricePerDay;


    public ParkingSlot(SizeOfSlot sizeOfSlot) {
        this.sizeOfSlot = sizeOfSlot;

    }

    public SizeOfSlot getSizeOfSlot(){
        return sizeOfSlot;
    }

    public Integer getParkingSlotID() {
        return parkingSlotID;
    }

    public Integer getPrice(){
        return pricePerDay;
    }
    public void setPrice(Integer price) {
        this.pricePerDay = price;
    }
    public void setParkingSlotID(Integer parkingSlotID) {
        this.parkingSlotID = parkingSlotID;
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
