package com.kurylchyk.model.services.impl.parkingSlotCommand;

import com.kurylchyk.model.dao.ParkingSlotDAO;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.domain.parkingSlots.ParkingSlot;
import com.kurylchyk.model.domain.parkingSlots.slotEnum.SlotSize;
import com.kurylchyk.model.domain.parkingSlots.slotEnum.SlotStatus;
import com.kurylchyk.model.services.impl.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AddParkingSlotCommand implements Command<Void> {

    private ParkingSlotDAO parkingSlotDAO;
    private SlotSize slotSize;
    private SlotStatus slotStatus;
    private Logger logger = LogManager.getLogger(AddParkingSlotCommand.class);


    public AddParkingSlotCommand(SlotSize slotSize, SlotStatus slotStatus){
        this.slotSize =slotSize;
        this.slotStatus =slotStatus;
        parkingSlotDAO = new ParkingSlotDAO();
    }

    AddParkingSlotCommand(SlotSize slotSize, SlotStatus slotStatus,ParkingSlotDAO parkingSlotDAO){
        this.slotSize =slotSize;
        this.slotStatus =slotStatus;
        this.parkingSlotDAO = parkingSlotDAO;
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
