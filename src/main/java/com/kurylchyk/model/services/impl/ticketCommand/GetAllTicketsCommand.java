package com.kurylchyk.model.services.impl.ticketCommand;

import com.kurylchyk.model.dao.ParkingTicketDAO;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.parkingTicket.ParkingTicket;
import com.kurylchyk.model.services.impl.Command;
import com.kurylchyk.model.parkingTicket.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class GetAllTicketsCommand implements Command<List<ParkingTicket>> {

    private ParkingTicketDAO parkingTicketDAO = new ParkingTicketDAO();
    private Status status;
    private static final Logger logger = LogManager.getLogger(GetAllTicketsCommand.class);
    public GetAllTicketsCommand(){}
    public GetAllTicketsCommand(Status status){
        this.status = status;
    }

    @Override
    public List<ParkingTicket> execute() throws ParkingSystemException {
        List<ParkingTicket> allTickets;
        if(status!=null){
            allTickets = parkingTicketDAO.selectAll(status);
            logger.debug("All tickets were selected by status");
        }else
        {
            allTickets = parkingTicketDAO.selectAll();
            logger.debug("All tickets were selected ");
        }
        return allTickets;
    }
}
