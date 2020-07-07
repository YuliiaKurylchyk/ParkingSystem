package com.kurylchyk.model.services.impl.ticketCommand;

import com.kurylchyk.model.parkingSlots.SlotStatus;
import com.kurylchyk.model.services.ParkingSlotService;
import com.kurylchyk.model.services.impl.Command;
import com.kurylchyk.model.dao.ParkingTicketDAO;
import com.kurylchyk.model.parkingTicket.ParkingTicket;
import com.kurylchyk.model.services.impl.ParkingSlotServiceImpl;

public class SaveParkingTicketCommand implements Command<ParkingTicket> {

    private ParkingTicketDAO parkingTicketDAO = new ParkingTicketDAO();
    ParkingSlotService parkingLotService  = new ParkingSlotServiceImpl();
    private ParkingTicket parkingTicket;

    public SaveParkingTicketCommand(ParkingTicket parkingTicket){
        this.parkingTicket = parkingTicket;
    }
    @Override
    public ParkingTicket execute() throws Exception {

        parkingLotService.updateStatus(parkingTicket.getParkingSlot(), SlotStatus.OCCUPIED);
        Integer parkingTicketID =  parkingTicketDAO.insert(parkingTicket);
        parkingTicket.setParkingTicketID(parkingTicketID);
        return parkingTicket;
    }
}
