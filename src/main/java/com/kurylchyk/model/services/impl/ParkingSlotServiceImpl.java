package com.kurylchyk.model.services.impl;

import com.kurylchyk.model.exceptions.NoAvailableParkingSlotException;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.domain.parkingSlots.ParkingSlot;
import com.kurylchyk.model.domain.parkingSlots.slotEnum.SlotSize;
import com.kurylchyk.model.domain.parkingSlots.slotEnum.SlotStatus;
import com.kurylchyk.model.domain.parkingTicket.ParkingTicket;
import com.kurylchyk.model.services.ParkingSlotService;
import com.kurylchyk.model.services.impl.parkingSlotCommand.*;
import com.kurylchyk.model.domain.vehicles.Vehicle;
import com.kurylchyk.model.services.impl.parkingSlotDTO.ParkingSlotDTO;

import java.util.List;
import java.util.Map;

public class ParkingSlotServiceImpl implements ParkingSlotService {

    private CommandExecutor executor = new CommandExecutor();

    @Override
    public void addSlot(SlotSize slotSize, SlotStatus slotStatus) throws ParkingSystemException {
        executor.execute(new AddParkingSlotCommand(slotSize, slotStatus));
    }

    @Override
    public void deleteSlot(ParkingSlot parkingSlot) throws ParkingSystemException {
        executor.execute(new DeleteParkingSlotCommand(parkingSlot));
    }

    @Override
    public ParkingSlot updateStatus(ParkingSlot parkingSlot, SlotStatus slotStatus)
            throws ParkingSystemException {
        ParkingSlot updatedSlot = executor.execute(new UpdateSlotStatusCommand(parkingSlot, slotStatus));
        return updatedSlot;
    }

    @Override
    public Map<SlotSize,Integer> getSlotsPrice() throws ParkingSystemException {
        return executor.execute(new GetSlotPricesCommand());
    }

    @Override
    public ParkingSlot getParkingSlot(ParkingSlotDTO identifier) throws ParkingSystemException {
        return executor.execute(new GetParkingSlotCommand(identifier));
    }

    @Override
    public List<ParkingSlot> getAll(SlotSize slotSize) throws ParkingSystemException {

        return executor.execute(new GetAllSlotsCommand(slotSize));
    }

    @Override
    public List<ParkingSlot> getAll() throws ParkingSystemException {
        return executor.execute(new GetAllSlotsCommand());
    }

    @Override
    public void changeSlot(ParkingTicket parkingTicket, ParkingSlot parkingSlot) throws ParkingSystemException {
        executor.execute(new ChangeParkingSlotCommand(parkingTicket, parkingSlot));

    }

    @Override
    public Integer countAvailableSlot(SlotSize slotSize) throws ParkingSystemException {
        return executor.execute(new CountAvailableSlotCommand(slotSize));
    }

    public List<ParkingSlot> getAvailableSlots(SlotSize slotSize) throws ParkingSystemException {

        return executor.execute(new GetAvailableSlotsCommand(slotSize));
    }

    @Override
    public List<ParkingSlot> getAvailableSlots(Vehicle vehicle) throws ParkingSystemException {

        SlotSize slotSize = executor.execute(new DefineParkingSlotCommand(vehicle));
        List<ParkingSlot> availableSlots = executor.execute(new GetAvailableSlotsCommand(slotSize));
        if (availableSlots.isEmpty()) {
            throw new NoAvailableParkingSlotException("No slots with appropriate size left");
        }
        return availableSlots;
    }

    public void updatePrice(Map<SlotSize,Integer> prices) throws ParkingSystemException {

        executor.execute(new UpdatePriceCommand(prices));

    }
}
