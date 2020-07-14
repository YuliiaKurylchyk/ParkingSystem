package com.kurylchyk.model.services.impl.parkingSlotCommand;

import com.kurylchyk.model.dao.ParkingSlotDAO;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.domain.parkingSlots.ParkingSlot;
import com.kurylchyk.model.domain.parkingSlots.slotEnum.SlotSize;
import com.kurylchyk.model.services.impl.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.List;

public class GetAvailableSlotsCommand implements Command<List<ParkingSlot>> {

    private SlotSize slotSize;
    private ParkingSlotDAO parkingSlotDAO;
    private static final Logger logger = LogManager.getLogger(GetAvailableSlotsCommand.class);

    public GetAvailableSlotsCommand(SlotSize slotSize){

        this.slotSize = slotSize;
        parkingSlotDAO = new ParkingSlotDAO();

    }

    GetAvailableSlotsCommand(SlotSize slotSize,ParkingSlotDAO parkingSlotDAO){

        this.slotSize = slotSize;
        this.parkingSlotDAO = parkingSlotDAO;

    }

    @Override
    public List<ParkingSlot> execute() throws ParkingSystemException {
        logger.debug("Getting available slots");
       return parkingSlotDAO.selectAvailable(slotSize);
    }
}
