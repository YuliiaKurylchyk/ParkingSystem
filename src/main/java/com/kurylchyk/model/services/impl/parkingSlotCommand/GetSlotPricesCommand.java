package com.kurylchyk.model.services.impl.parkingSlotCommand;

import com.kurylchyk.model.dao.ParkingSlotDAO;
import com.kurylchyk.model.domain.parkingSlots.slotEnum.SlotSize;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.services.impl.Command;
import java.util.Map;

public class GetSlotPricesCommand implements Command<Map<SlotSize,Integer>> {
    private ParkingSlotDAO parkingSlotDAO;

    public GetSlotPricesCommand(){
        parkingSlotDAO = new ParkingSlotDAO();
    }


    GetSlotPricesCommand(ParkingSlotDAO parkingSlotDAO){
       this.parkingSlotDAO = parkingSlotDAO;
    }



    @Override
    public Map<SlotSize,Integer> execute() throws ParkingSystemException {

        return parkingSlotDAO.getSlotsPrice();
    }
}
