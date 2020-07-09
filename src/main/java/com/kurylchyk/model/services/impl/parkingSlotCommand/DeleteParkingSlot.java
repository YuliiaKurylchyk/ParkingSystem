package com.kurylchyk.model.services.impl.parkingSlotCommand;

import com.kurylchyk.model.dao.ParkingSlotDAO;
import com.kurylchyk.model.exceptions.CurrentSlotIsOccupiedException;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.parkingSlots.ParkingSlot;
import com.kurylchyk.model.parkingSlots.SlotStatus;
import com.kurylchyk.model.services.impl.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DeleteParkingSlot implements Command<Void> {

    private ParkingSlot parkingSlot;
    private ParkingSlotDAO parkingSlotDAO = new ParkingSlotDAO();
    private Logger logger = LogManager.getLogger(DeleteParkingSlot.class);


    public DeleteParkingSlot(ParkingSlot parkingSlot){
        this.parkingSlot = parkingSlot;
    }

    @Override
    public Void execute() throws ParkingSystemException {

        if(parkingSlot.getStatus().equals(SlotStatus.OCCUPIED)){
            throw  new CurrentSlotIsOccupiedException("Slot "+parkingSlot + " is occupied!");
        }else{
         parkingSlotDAO.delete(parkingSlot);
         logger.info("Parking slot "+parkingSlot + " was deleted");
        }
        return null;
    }
}
