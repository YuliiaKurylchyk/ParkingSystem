package com.kurylchyk.model.services.impl.ticketCommand;

import com.kurylchyk.model.services.impl.Command;
import com.kurylchyk.model.dao.ParkingTicketDAO;
import com.kurylchyk.model.parkingTicket.ParkingTicket;

public class UpdateTicket  implements Command<ParkingTicket> {

    private ParkingTicketDAO parkingTicketDAO = new ParkingTicketDAO();
    private ParkingTicket parkingTicket;


    public UpdateTicket(ParkingTicket parkingTicket){
        this.parkingTicket = parkingTicket;

    }

    @Override
    public ParkingTicket execute() throws Exception {
        parkingTicketDAO.update(parkingTicket,parkingTicket.getParkingTicketID());
        return parkingTicket;
    }
}
