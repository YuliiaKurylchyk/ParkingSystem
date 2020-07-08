package com.kurylchyk.model.services.impl.parkingSlotCommand;

import com.kurylchyk.model.dao.ParkingSlotDAO;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.parkingSlots.ParkingSlot;
import com.kurylchyk.model.parkingSlots.SlotSize;
import com.kurylchyk.model.services.impl.Command;

import java.util.List;

public class GetAvailableSlotsCommand implements Command<List<ParkingSlot>> {

    private SlotSize slotSize;
    private ParkingSlotDAO parkingSlotDAO = new ParkingSlotDAO();

    public GetAvailableSlotsCommand(SlotSize slotSize){
        this.slotSize = slotSize;
    }

    @Override
    public List<ParkingSlot> execute() throws ParkingSystemException {
       return parkingSlotDAO.selectAvailable(slotSize);
    }
}
