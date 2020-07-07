package com.kurylchyk.model.services.impl.ticketCommand;

import com.kurylchyk.model.dao.ParkingSlotDAO;
import com.kurylchyk.model.dao.ParkingSlotIdentifier;
import com.kurylchyk.model.parkingSlots.SlotStatus;
import com.kurylchyk.model.services.ParkingLotService;
import com.kurylchyk.model.services.impl.Command;
import com.kurylchyk.model.dao.ParkingTicketDAO;
import com.kurylchyk.model.parkingTicket.ParkingTicket;
import com.kurylchyk.model.services.impl.ParkingLotServiceImpl;

public class SaveParkingTicketCommand implements Command<ParkingTicket> {

    private ParkingTicketDAO parkingTicketDAO = new ParkingTicketDAO();
    ParkingLotService parkingLotService  = new ParkingLotServiceImpl();
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
