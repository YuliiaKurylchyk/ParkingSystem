package com.kurylchyk.model.services;

import com.kurylchyk.model.parkingSlots.ParkingSlot;
import com.kurylchyk.model.parkingSlots.SlotSize;

public class ParkingSlotPriceDTO {
    private SlotSize slotSize;
    private Integer price;

    public ParkingSlotPriceDTO(SlotSize slotSize, Integer price){
        this.slotSize = slotSize;
        this.price = price;
    }

    public SlotSize getSlotSize() {
        return slotSize;
    }

    public Integer getPrice() {
        return price;
    }
}
