package com.kurylchyk.model.services;

import com.kurylchyk.model.dao.ParkingSlotIdentifier;
import com.kurylchyk.model.parkingSlots.ParkingSlot;
import com.kurylchyk.model.parkingSlots.SlotSize;
import com.kurylchyk.model.parkingSlots.SlotStatus;
import com.kurylchyk.model.services.impl.parkingSlotCommand.ParkingSlotDefiner;
import com.kurylchyk.model.vehicles.Vehicle;

import java.util.List;

public interface ParkingLotService {

    List<ParkingSlot> getAll() throws Exception;

    ParkingSlot getParkingSlot(ParkingSlotIdentifier identifier) throws Exception;

    List<ParkingSlot> getAvailableSlots(Vehicle vehicle) throws Exception;

    List<ParkingSlot> getAll(SlotSize slotSize) throws Exception;

   void addSlot(SlotSize slotSize, SlotStatus slotStatus) throws Exception;

   void deleteSlot(ParkingSlot parkingSlot) throws  Exception;

    void updateStatus(ParkingSlot parkingSlot, SlotStatus slotStatus) throws Exception;

    List<ParkingSlotPriceDTO> getSlotsPrice() throws Exception;

}
