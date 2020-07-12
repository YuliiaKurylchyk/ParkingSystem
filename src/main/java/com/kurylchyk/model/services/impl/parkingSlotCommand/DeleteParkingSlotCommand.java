package com.kurylchyk.model.services.impl.parkingSlotCommand;

import com.kurylchyk.model.dao.ParkingSlotDAO;
import com.kurylchyk.model.exceptions.CurrentSlotIsOccupiedException;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.domain.parkingSlots.ParkingSlot;
import com.kurylchyk.model.domain.parkingSlots.slotEnum.SlotStatus;
import com.kurylchyk.model.services.impl.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DeleteParkingSlotCommand implements Command<Void> {

    private ParkingSlot parkingSlot;
    private ParkingSlotDAO parkingSlotDAO;
    private Logger logger = LogManager.getLogger(DeleteParkingSlotCommand.class);


    public DeleteParkingSlotCommand(ParkingSlot parkingSlot){

        this.parkingSlot = parkingSlot;
        parkingSlotDAO = new ParkingSlotDAO();
    }

    DeleteParkingSlotCommand(ParkingSlot parkingSlot,ParkingSlotDAO parkingSlotDAO){

        this.parkingSlot = parkingSlot;
        this.parkingSlotDAO = parkingSlotDAO;
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
