package com.kurylchyk.model.services.impl;

import com.kurylchyk.model.dao.ParkingSlotIdentifier;
import com.kurylchyk.model.parkingSlots.ParkingSlot;
import com.kurylchyk.model.parkingSlots.SlotSize;
import com.kurylchyk.model.parkingSlots.SlotStatus;
import com.kurylchyk.model.services.ParkingLotService;
import com.kurylchyk.model.services.ParkingSlotPriceDTO;
import com.kurylchyk.model.services.impl.parkingSlotCommand.*;
import com.kurylchyk.model.vehicles.Vehicle;
import com.kurylchyk.model.vehicles.VehicleType;

import java.util.List;

public class ParkingLotServiceImpl  implements ParkingLotService {
    private CommandExecutor executor = new CommandExecutor();


    public void addSlot(SlotSize slotSize, SlotStatus slotStatus) throws Exception {

        executor.execute(new AddParkingSlotCommand(slotSize, slotStatus));
    }

    @Override
    public void deleteSlot(ParkingSlot parkingSlot) throws Exception {
        executor.execute(new DeleteParkingSlot(parkingSlot));
    }

    @Override
    public void updateStatus(ParkingSlot parkingSlot, SlotStatus slotStatus) throws Exception {

        executor.execute(new UpdateSlotStatusCommand(parkingSlot,slotStatus));
    }

    @Override
    public List<ParkingSlotPriceDTO> getSlotsPrice() throws Exception {
        return executor.execute(new GetSlotPriceCommand());
    }

    @Override
    public ParkingSlot getParkingSlot(ParkingSlotIdentifier identifier) throws Exception {
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
    public List<ParkingSlot> getAvailableSlots(Vehicle vehicle) throws Exception {
       SlotSize slotSize =  executor.execute(new DefineParkingSlotCommand(vehicle));
       return executor.execute(new GetAvailableSlotsCommand(slotSize));
    }
}
