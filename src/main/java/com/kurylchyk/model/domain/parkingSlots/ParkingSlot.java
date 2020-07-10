package com.kurylchyk.model.domain.parkingSlots;

import com.kurylchyk.model.domain.parkingSlots.slotEnum.SlotSize;
import com.kurylchyk.model.domain.parkingSlots.slotEnum.SlotStatus;

public class ParkingSlot {

    private Integer parkingSlotID;
    private final SlotSize sizeOfSlot;
    private SlotStatus status;
    private Integer price;

    public ParkingSlot(SlotSize sizeOfSlot) {
        this.sizeOfSlot = sizeOfSlot;

    }

    public SlotSize getSizeOfSlot() {
        return sizeOfSlot;
    }

    public Integer getParkingSlotID() {
        return parkingSlotID;
    }

    public void setParkingSlotID(Integer parkingSlotID) {
        this.parkingSlotID = parkingSlotID;
    }

    public SlotStatus getStatus() {
        return status;
    }

    public void setStatus(SlotStatus status) {
        this.status = status;
    }


    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public ParkingSlot(Integer parkingSlotID, SlotSize sizeOfSlot, SlotStatus status, Integer price) {
        this.parkingSlotID = parkingSlotID;
        this.sizeOfSlot = sizeOfSlot;
        this.status = status;
        this.price = price;
    }

    public ParkingSlot(Integer parkingSlotID,SlotSize slotSize,SlotStatus slotStatus){
        this.parkingSlotID = parkingSlotID;
        this.sizeOfSlot = slotSize;
        this.status = slotStatus;
    }
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + sizeOfSlot.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {

        ParkingSlot anotherParkingSlot = (ParkingSlot) obj;
        return this.sizeOfSlot.equals(anotherParkingSlot.sizeOfSlot);
    }

    @Override
    public String toString() {
        return sizeOfSlot.toString() +"_"+ parkingSlotID;
    }
}
