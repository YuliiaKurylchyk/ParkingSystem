package com.kurylchyk.model.services.impl.parkingSlotCommand;

import com.kurylchyk.model.dao.ParkingSlotDAO;
import com.kurylchyk.model.services.impl.parkingSlotDTO.ParkingSlotDTO;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.domain.parkingSlots.ParkingSlot;
import com.kurylchyk.model.domain.parkingSlots.slotEnum.SlotStatus;
import com.kurylchyk.model.services.impl.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UpdateSlotStatusCommand implements Command<ParkingSlot> {

    private ParkingSlotDAO parkingSlotDAO = new ParkingSlotDAO();
    private ParkingSlot parkingSlot;
    private SlotStatus slotStatus;
    private static final Logger logger = LogManager.getLogger(UpdateSlotStatusCommand.class);


    public UpdateSlotStatusCommand(ParkingSlot parkingSlot, SlotStatus slotStatus){
        this.parkingSlot = parkingSlot;
        this.slotStatus = slotStatus;
    }

    @Override
    public ParkingSlot execute() throws ParkingSystemException {

        parkingSlot.setStatus(slotStatus);
        parkingSlotDAO.update(parkingSlot,new ParkingSlotDTO(parkingSlot.getSizeOfSlot(),parkingSlot.getParkingSlotID()));
        logger.info("Status of parking slot "+parkingSlot +" was changed to "+slotStatus);
        return parkingSlot;
    }
}
