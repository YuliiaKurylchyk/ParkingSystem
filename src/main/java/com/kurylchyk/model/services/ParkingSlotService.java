package com.kurylchyk.model.services;

import com.kurylchyk.model.services.impl.parkingSlotDTOs.ParkingSlotDTO;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.domain.parkingSlots.ParkingSlot;
import com.kurylchyk.model.domain.parkingSlots.slotEnum.SlotSize;
import com.kurylchyk.model.domain.parkingSlots.slotEnum.SlotStatus;
import com.kurylchyk.model.domain.parkingTicket.ParkingTicket;
import com.kurylchyk.model.domain.vehicles.Vehicle;
import com.kurylchyk.model.services.impl.parkingSlotDTOs.ParkingSlotPriceDTO;

import java.util.List;

public interface ParkingSlotService {

    List<ParkingSlot> getAll() throws ParkingSystemException;

    ParkingSlot getParkingSlot(ParkingSlotDTO identifier) throws ParkingSystemException;

    List<ParkingSlot> getAvailableSlots(SlotSize slotSize) throws ParkingSystemException;

    List<ParkingSlot> getAvailableSlots(Vehicle vehicle) throws ParkingSystemException;

    List<ParkingSlot> getAll(SlotSize slotSize) throws ParkingSystemException;

    void changeSlot(ParkingTicket parkingTicket, ParkingSlot parkingSlot) throws ParkingSystemException;

   void addSlot(SlotSize slotSize, SlotStatus slotStatus) throws ParkingSystemException;

   void deleteSlot(ParkingSlot parkingSlot) throws ParkingSystemException;

   void updatePrice(List<ParkingSlotPriceDTO> prices) throws ParkingSystemException;

    ParkingSlot updateStatus(ParkingSlot parkingSlot, SlotStatus slotStatus) throws ParkingSystemException;

    List<ParkingSlotPriceDTO> getSlotsPrice() throws ParkingSystemException;

}
