package com.kurylchyk.model.services.impl.parkingSlotCommand;

import com.kurylchyk.model.dao.ParkingSlotDAO;
import com.kurylchyk.model.dao.ParkingSlotDTO;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.parkingSlots.ParkingSlot;
import com.kurylchyk.model.parkingSlots.SlotStatus;
import com.kurylchyk.model.services.impl.Command;

public class UpdateSlotStatusCommand implements Command<ParkingSlot> {

    private ParkingSlotDAO parkingSlotDAO = new ParkingSlotDAO();
    private ParkingSlot parkingSlot;
    private SlotStatus slotStatus;


    public UpdateSlotStatusCommand(ParkingSlot parkingSlot, SlotStatus slotStatus){
        this.parkingSlot = parkingSlot;
        this.slotStatus = slotStatus;
    }

    @Override
    public ParkingSlot execute() throws ParkingSystemException {

        parkingSlot.setStatus(slotStatus);
        parkingSlotDAO.update(parkingSlot,new ParkingSlotDTO(parkingSlot.getSizeOfSlot(),parkingSlot.getParkingSlotID()));
        return parkingSlot;
    }
}
