package com.kurylchyk.model.services.impl.parkingSlotCommand;

import com.kurylchyk.model.dao.ParkingSlotDAO;
import com.kurylchyk.model.exceptions.CurrentSlotIsOccupiedException;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.parkingSlots.ParkingSlot;
import com.kurylchyk.model.parkingSlots.SlotStatus;
import com.kurylchyk.model.services.impl.Command;

public class DeleteParkingSlot implements Command<Void> {

    private ParkingSlot parkingSlot;
    private ParkingSlotDAO parkingSlotDAO = new ParkingSlotDAO();


    public DeleteParkingSlot(ParkingSlot parkingSlot){
        this.parkingSlot = parkingSlot;
    }

    @Override
    public Void execute() throws ParkingSystemException {

        if(parkingSlot.getStatus().equals(SlotStatus.OCCUPIED)){
            throw  new CurrentSlotIsOccupiedException("Slot "+parkingSlot + " is occupied!");
        }else{
         parkingSlotDAO.delete(parkingSlot);
        }
        return null;
    }
}
