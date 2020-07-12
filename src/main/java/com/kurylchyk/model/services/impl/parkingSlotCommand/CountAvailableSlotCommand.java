package com.kurylchyk.model.services.impl.parkingSlotCommand;

import com.kurylchyk.model.dao.ParkingSlotDAO;
import com.kurylchyk.model.domain.parkingSlots.slotEnum.SlotSize;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.services.impl.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CountAvailableSlotCommand implements Command<Integer> {

    private SlotSize slotSize;
    private ParkingSlotDAO parkingSlotDAO;
    private static final Logger logger = LogManager.getLogger(CountAvailableSlotCommand.class);

    public CountAvailableSlotCommand(SlotSize slotSize){
        this.slotSize = slotSize;
        parkingSlotDAO = new ParkingSlotDAO();
    }

    CountAvailableSlotCommand(SlotSize slotSize,ParkingSlotDAO parkingSlotDAO){
        this.slotSize = slotSize;
        this.parkingSlotDAO = parkingSlotDAO;
    }

    @Override
    public Integer execute() throws ParkingSystemException {
        Integer countOfAvailable = parkingSlotDAO.countOfAvailable(slotSize);
        logger.info("Counted available " +slotSize+ " slots");
        return countOfAvailable;
    }
}
