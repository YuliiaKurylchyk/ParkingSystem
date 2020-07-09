package com.kurylchyk.model.services.impl.parkingSlotCommand;

import com.kurylchyk.model.dao.ParkingSlotDAO;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.parkingSlots.ParkingSlot;
import com.kurylchyk.model.parkingSlots.SlotSize;
import com.kurylchyk.model.parkingSlots.SlotStatus;
import com.kurylchyk.model.services.impl.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AddParkingSlotCommand implements Command<Void> {

    private ParkingSlotDAO parkingSlotDAO = new ParkingSlotDAO();
    private SlotSize slotSize;
    private SlotStatus slotStatus;
    private Logger logger = LogManager.getLogger(AddParkingSlotCommand.class);


    public AddParkingSlotCommand(SlotSize slotSize, SlotStatus slotStatus){
        this.slotSize =slotSize;
        this.slotStatus =slotStatus;
    }

    @Override
    public Void execute() throws ParkingSystemException {
        Integer lastID = parkingSlotDAO.getLastID(slotSize);
        ParkingSlot parkingSlot = new ParkingSlot(lastID+1,slotSize,slotStatus);
        parkingSlotDAO.insert(parkingSlot);
        logger.info("New " +parkingSlot + " parking slot was added");
        return null;
    }
}
