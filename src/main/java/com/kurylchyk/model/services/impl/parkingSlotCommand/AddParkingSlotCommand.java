package com.kurylchyk.model.services.impl.parkingSlotCommand;

import com.kurylchyk.model.dao.ParkingSlotDAO;
import com.kurylchyk.model.parkingSlots.ParkingSlot;
import com.kurylchyk.model.parkingSlots.SlotSize;
import com.kurylchyk.model.parkingSlots.SlotStatus;
import com.kurylchyk.model.services.impl.Command;

public class AddParkingSlotCommand implements Command<Void> {

    private ParkingSlotDAO parkingSlotDAO = new ParkingSlotDAO();
    private SlotSize slotSize;
    private SlotStatus slotStatus;


    public AddParkingSlotCommand(SlotSize slotSize, SlotStatus slotStatus){
        this.slotSize =slotSize;
        this.slotStatus =slotStatus;
    }

    @Override
    public Void execute() throws Exception {
        Integer lastID = parkingSlotDAO.getLastID(slotSize);
        ParkingSlot parkingSlot = new ParkingSlot(lastID+1,slotSize,slotStatus);
        parkingSlotDAO.insert(parkingSlot);
        return null;
    }
}
