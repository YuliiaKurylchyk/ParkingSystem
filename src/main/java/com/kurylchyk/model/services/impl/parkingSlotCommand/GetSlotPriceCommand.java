package com.kurylchyk.model.services.impl.parkingSlotCommand;

import com.kurylchyk.model.dao.ParkingSlotDAO;
import com.kurylchyk.model.domain.parkingSlots.slotEnum.SlotSize;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.services.impl.Command;

import java.util.List;
import java.util.Map;

public class GetSlotPriceCommand implements Command<Map<SlotSize,Integer>> {
    private ParkingSlotDAO parkingSlotDAO = new ParkingSlotDAO();

    @Override
    public Map<SlotSize,Integer> execute() throws ParkingSystemException {

        return parkingSlotDAO.getSlotsPrice();
    }
}
