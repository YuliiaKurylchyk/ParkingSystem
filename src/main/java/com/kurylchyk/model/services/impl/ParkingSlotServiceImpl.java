package com.kurylchyk.model.services.impl;

import com.kurylchyk.model.dao.ParkingSlotDTO;
import com.kurylchyk.model.parkingSlots.ParkingSlot;
import com.kurylchyk.model.parkingSlots.SlotSize;
import com.kurylchyk.model.parkingSlots.SlotStatus;
import com.kurylchyk.model.parkingTicket.ParkingTicket;
import com.kurylchyk.model.services.ParkingSlotService;
import com.kurylchyk.model.services.ParkingSlotPriceDTO;
import com.kurylchyk.model.services.impl.parkingSlotCommand.*;
import com.kurylchyk.model.vehicles.Vehicle;

import java.util.List;

public class ParkingSlotServiceImpl implements ParkingSlotService {
    private CommandExecutor executor = new CommandExecutor();


    @Override
    public void addSlot(SlotSize slotSize, SlotStatus slotStatus) throws Exception {

        executor.execute(new AddParkingSlotCommand(slotSize, slotStatus));
    }

    @Override
    public void deleteSlot(ParkingSlot parkingSlot) throws Exception {
        executor.execute(new DeleteParkingSlot(parkingSlot));
    }

    @Override
    public ParkingSlot updateStatus(ParkingSlot parkingSlot, SlotStatus slotStatus)
            throws Exception {

        return executor.execute(new UpdateSlotStatusCommand(parkingSlot,slotStatus));
    }

    @Override
    public List<ParkingSlotPriceDTO> getSlotsPrice() throws Exception {
        return executor.execute(new GetSlotPriceCommand());
    }

    @Override
    public ParkingSlot getParkingSlot(ParkingSlotDTO identifier) throws Exception {
        return executor.execute(new GetParkingSlotCommand(identifier));
    }
    @Override
    public List<ParkingSlot> getAll(SlotSize slotSize) throws Exception {

        return executor.execute(new GetAllSlotsCommand(slotSize));
    }
    @Override
    public List<ParkingSlot> getAll() throws Exception {
        return executor.execute(new GetAllSlotsCommand());
    }

    @Override
    public void changeSlot(ParkingTicket parkingTicket, ParkingSlot parkingSlot) throws Exception{
        executor.execute(new ChangeParkingSlotCommand(parkingTicket,parkingSlot));
    }
    public  List<ParkingSlot> getAvailableSlots(SlotSize slotSize) throws Exception {
        return executor.execute(new GetAvailableSlotsCommand(slotSize));
    }

    @Override
    public List<ParkingSlot> getAvailableSlots(Vehicle vehicle) throws Exception {
       SlotSize slotSize =  executor.execute(new DefineParkingSlotCommand(vehicle));
       return executor.execute(new GetAvailableSlotsCommand(slotSize));
    }
}
