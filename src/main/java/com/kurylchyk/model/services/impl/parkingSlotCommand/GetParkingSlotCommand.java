package com.kurylchyk.model.services.impl.parkingSlotCommand;

import com.kurylchyk.model.dao.ParkingSlotDAO;
import com.kurylchyk.model.services.impl.parkingSlotDTO.ParkingSlotDTO;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.domain.parkingSlots.ParkingSlot;
import com.kurylchyk.model.services.impl.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class GetParkingSlotCommand implements Command<ParkingSlot> {

    private ParkingSlotDAO parkingSlotDAO;
    private ParkingSlotDTO parkingSlotDTO;
    private static Logger logger = LogManager.getLogger(GetParkingSlotCommand.class);

    public GetParkingSlotCommand(ParkingSlotDTO parkingSlotDTO){
        this.parkingSlotDTO = parkingSlotDTO;
        parkingSlotDAO = new ParkingSlotDAO();
    }

    GetParkingSlotCommand(ParkingSlotDTO parkingSlotDTO,ParkingSlotDAO parkingSlotDAO){
        this.parkingSlotDTO = parkingSlotDTO;
        this.parkingSlotDAO = parkingSlotDAO;
    }

    @Override
    public ParkingSlot execute() throws ParkingSystemException {
        ParkingSlot parkingSlot = parkingSlotDAO.select(parkingSlotDTO).get();
        logger.debug("Got parking slot from database");
        return  parkingSlot;
    }
}
