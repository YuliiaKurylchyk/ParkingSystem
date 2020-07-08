package com.kurylchyk.model.services.impl.parkingSlotCommand;

import com.kurylchyk.model.dao.ParkingSlotDAO;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.parkingSlots.ParkingSlot;
import com.kurylchyk.model.parkingSlots.SlotSize;
import com.kurylchyk.model.services.impl.Command;
import com.kurylchyk.model.vehicles.Vehicle;

public class DefineParkingSlotCommand implements Command<SlotSize> {

    private ParkingSlotDAO parkingSlotDAO = new ParkingSlotDAO();
    private Vehicle vehicle;


   public DefineParkingSlotCommand(Vehicle vehicle){
        this.vehicle = vehicle;
    }

    @Override
    public SlotSize execute() throws ParkingSystemException {
        SlotSize slotSize = ParkingSlotDefiner.defineSlotSize(vehicle);
        return slotSize;
    }
}
