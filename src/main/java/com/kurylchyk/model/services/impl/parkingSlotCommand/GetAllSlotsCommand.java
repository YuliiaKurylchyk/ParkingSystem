package com.kurylchyk.model.services.impl.parkingSlotCommand;

import com.kurylchyk.model.dao.ParkingSlotDAO;
import com.kurylchyk.model.parkingSlots.ParkingSlot;
import com.kurylchyk.model.parkingSlots.SlotSize;
import com.kurylchyk.model.services.impl.Command;
import com.kurylchyk.model.services.impl.ticketCommand.GetAllTicketCommand;

import java.util.List;

import javax.servlet.http.Cookie;

public class GetAllSlotsCommand implements Command<List<ParkingSlot>> {

    private SlotSize slotSize;
    private ParkingSlotDAO parkingSlotDAO = new ParkingSlotDAO();

    public GetAllSlotsCommand(SlotSize slotSize) {
        this.slotSize = slotSize;
    }

    public  GetAllSlotsCommand(){}

    @Override
    public List<ParkingSlot> execute() throws Exception {

        List<ParkingSlot> allSlots;
        if(slotSize!=null) {
            allSlots =parkingSlotDAO.selectAll(slotSize);
        }else{
            allSlots = parkingSlotDAO.selectAll();
        }
        return allSlots;
    }
}
