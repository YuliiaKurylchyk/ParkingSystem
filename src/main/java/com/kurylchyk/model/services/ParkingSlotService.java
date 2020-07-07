package com.kurylchyk.model.services;

import com.kurylchyk.model.dao.ParkingSlotDTO;
import com.kurylchyk.model.parkingSlots.ParkingSlot;
import com.kurylchyk.model.parkingSlots.SlotSize;
import com.kurylchyk.model.parkingSlots.SlotStatus;
import com.kurylchyk.model.parkingTicket.ParkingTicket;
import com.kurylchyk.model.vehicles.Vehicle;

import java.util.List;

public interface ParkingSlotService {

    List<ParkingSlot> getAll() throws Exception;

    ParkingSlot getParkingSlot(ParkingSlotDTO identifier) throws Exception;

    List<ParkingSlot> getAvailableSlots(SlotSize slotSize)throws Exception;

    List<ParkingSlot> getAvailableSlots(Vehicle vehicle) throws Exception;

    List<ParkingSlot> getAll(SlotSize slotSize) throws Exception;

    void changeSlot(ParkingTicket parkingTicket, ParkingSlot parkingSlot) throws  Exception;

   void addSlot(SlotSize slotSize, SlotStatus slotStatus) throws Exception;

   void deleteSlot(ParkingSlot parkingSlot) throws  Exception;

    ParkingSlot updateStatus(ParkingSlot parkingSlot, SlotStatus slotStatus) throws Exception;

    List<ParkingSlotPriceDTO> getSlotsPrice() throws Exception;

}
