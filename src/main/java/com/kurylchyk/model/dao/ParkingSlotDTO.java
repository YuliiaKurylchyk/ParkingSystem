package com.kurylchyk.model.dao;

import com.kurylchyk.model.parkingSlots.ParkingSlot;
import com.kurylchyk.model.parkingSlots.SlotSize;
import com.kurylchyk.model.parkingSlots.SlotStatus;

public class ParkingSlotDTO {
    private SlotSize slotSize;
    private Integer parkingSlotID;


    public SlotSize getSlotSize() {
        return slotSize;
    }

    public Integer getParkingSlotID() {
        return parkingSlotID;
    }

    public ParkingSlotDTO(SlotSize slotSize, Integer parkingSlotID){
        this.slotSize = slotSize;
        this.parkingSlotID = parkingSlotID;
    }
}
