package com.kurylchyk.model.services.impl.parkingSlotCommand;

import com.kurylchyk.model.dao.ParkingSlotDAO;
import com.kurylchyk.model.dao.ParkingSlotDTO;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.parkingSlots.ParkingSlot;
import com.kurylchyk.model.services.impl.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class GetParkingSlotCommand implements Command<ParkingSlot> {

    private ParkingSlotDAO parkingSlotDAO  = new ParkingSlotDAO();
    private ParkingSlotDTO parkingSlotIdentifier;
    private static Logger logger = LogManager.getLogger(GetParkingSlotCommand.class);

    public GetParkingSlotCommand(ParkingSlotDTO parkingSlotIdentifier){
        this.parkingSlotIdentifier = parkingSlotIdentifier;
    }

    @Override
    public ParkingSlot execute() throws ParkingSystemException {
        ParkingSlot parkingSlot = parkingSlotDAO.select(parkingSlotIdentifier).get();
        logger.debug("Got parking slot");
        return  parkingSlot;
    }
}
