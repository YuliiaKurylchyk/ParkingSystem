package com.kurylchyk.model.services.impl.parkingSlotCommand;

import com.kurylchyk.model.dao.ParkingSlotDAO;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.domain.parkingSlots.ParkingSlot;
import com.kurylchyk.model.domain.parkingSlots.slotEnum.SlotSize;
import com.kurylchyk.model.services.impl.Command;
import com.kurylchyk.model.services.impl.customerCommand.GetAllCustomersCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class GetAllSlotsCommand implements Command<List<ParkingSlot>> {

    private SlotSize slotSize;
    private ParkingSlotDAO parkingSlotDAO = new ParkingSlotDAO();
    private static Logger logger = LogManager.getLogger(GetAllCustomersCommand.class);

    public GetAllSlotsCommand(SlotSize slotSize) {
        this.slotSize = slotSize;
    }
    public  GetAllSlotsCommand(){}

    @Override
    public List<ParkingSlot> execute() throws ParkingSystemException {

        List<ParkingSlot> allSlots;
        if(slotSize!=null) {
            allSlots =parkingSlotDAO.selectAll(slotSize);
            logger.debug("Getting all "+slotSize+ " slots");
        }else{
            allSlots = parkingSlotDAO.selectAll();
            logger.debug("Getting all "+slotSize+ " slots");
        }

        return allSlots;
    }
}
