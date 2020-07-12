package com.kurylchyk.model.services.impl.ticketCommand;

import com.kurylchyk.model.services.impl.Command;
import com.kurylchyk.model.dao.ParkingTicketDAO;
import com.kurylchyk.model.exceptions.NoSuchParkingTicketException;
import com.kurylchyk.model.domain.parkingTicket.ParkingTicket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class GetParkingTicketCommand implements Command<ParkingTicket> {

    private ParkingTicketDAO parkingTicketDAO;
    private Integer parkingTicketID;
    private static final Logger logger  = LogManager.getLogger(GetParkingTicketCommand.class);

   public GetParkingTicketCommand(Integer parkingTicketID){

       this.parkingTicketID = parkingTicketID;
       parkingTicketDAO = new ParkingTicketDAO();
    }

    GetParkingTicketCommand(Integer parkingTicketID, ParkingTicketDAO parkingTicketDAO){
       this.parkingTicketID = parkingTicketID;
       this.parkingTicketDAO = parkingTicketDAO;
    }

    @Override
    public ParkingTicket execute() throws NoSuchParkingTicketException {
       logger.debug("Getting parking ticket with ID "+parkingTicketID);
      return parkingTicketDAO.select(parkingTicketID).orElseThrow(
              ()->new NoSuchParkingTicketException("No parking ticket with id " + parkingTicketID + " was found"));
    }
}
