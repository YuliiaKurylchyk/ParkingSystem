package com.kurylchyk.model.services.impl.parkingSlotCommand;

import com.kurylchyk.model.dao.ParkingSlotDAO;
import com.kurylchyk.model.dao.ParkingSlotDTO;
import com.kurylchyk.model.parkingSlots.ParkingSlot;
import com.kurylchyk.model.services.impl.Command;

public class GetParkingSlotCommand implements Command<ParkingSlot> {

    private ParkingSlotDAO parkingSlotDAO  = new ParkingSlotDAO();
    private ParkingSlotDTO parkingSlotIdentifier;

    public GetParkingSlotCommand(ParkingSlotDTO parkingSlotIdentifier){
        this.parkingSlotIdentifier = parkingSlotIdentifier;
    }

    @Override
    public ParkingSlot execute() throws Exception {
        ParkingSlot parkingSlot = parkingSlotDAO.select(parkingSlotIdentifier).get();
        return  parkingSlot;
    }
}
