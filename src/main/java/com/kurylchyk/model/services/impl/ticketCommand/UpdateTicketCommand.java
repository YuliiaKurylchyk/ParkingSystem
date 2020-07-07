package com.kurylchyk.model.services.impl.ticketCommand;

import com.kurylchyk.model.services.impl.Command;
import com.kurylchyk.model.dao.ParkingTicketDAO;
import com.kurylchyk.model.parkingTicket.ParkingTicket;

public class UpdateTicketCommand implements Command<ParkingTicket> {

    private ParkingTicketDAO parkingTicketDAO = new ParkingTicketDAO();
    private ParkingTicket parkingTicket;


    public UpdateTicketCommand(ParkingTicket parkingTicket){
        this.parkingTicket = parkingTicket;

    }

    @Override
    public ParkingTicket execute() throws Exception {
        parkingTicketDAO.update(parkingTicket,parkingTicket.getParkingTicketID());
        return parkingTicket;
    }
}
