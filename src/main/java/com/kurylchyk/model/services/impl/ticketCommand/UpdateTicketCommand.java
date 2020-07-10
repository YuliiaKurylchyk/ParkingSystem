package com.kurylchyk.model.services.impl.ticketCommand;

import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.services.impl.Command;
import com.kurylchyk.model.dao.ParkingTicketDAO;
import com.kurylchyk.model.domain.parkingTicket.ParkingTicket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UpdateTicketCommand implements Command<ParkingTicket> {

    private ParkingTicketDAO parkingTicketDAO = new ParkingTicketDAO();
    private ParkingTicket parkingTicket;
    private static final Logger logger = LogManager.getLogger(UpdateTicketCommand.class);


    public UpdateTicketCommand(ParkingTicket parkingTicket){
        this.parkingTicket = parkingTicket;

    }

    @Override
    public ParkingTicket execute() throws ParkingSystemException {
        parkingTicketDAO.update(parkingTicket,parkingTicket.getParkingTicketID());
        logger.info("Parking ticket "+ parkingTicket.getParkingTicketID() + " was updated");
        return parkingTicket;
    }
}
