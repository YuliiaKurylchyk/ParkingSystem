package com.kurylchyk.model.services.impl.parkingSlotDTO;

import com.kurylchyk.model.domain.parkingSlots.slotEnum.SlotSize;

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
