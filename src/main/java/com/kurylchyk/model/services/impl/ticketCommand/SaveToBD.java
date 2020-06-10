package com.kurylchyk.model.services.impl.ticketCommand;

import com.kurylchyk.model.services.impl.Command;
import com.kurylchyk.model.dao.ParkingTicketDAO;
import com.kurylchyk.model.parkingTicket.ParkingTicket;

public class SaveToBD implements Command<ParkingTicket> {

    private ParkingTicketDAO parkingTicketDAO = new ParkingTicketDAO();
    private ParkingTicket parkingTicket;

    public SaveToBD(ParkingTicket parkingTicket){
        this.parkingTicket = parkingTicket;
    }
    @Override
    public ParkingTicket execute() {
        Integer parkingTicketID =  parkingTicketDAO.insert(parkingTicket);
        parkingTicket.setParkingTicketID(parkingTicketID);
        return parkingTicket;
    }
}
