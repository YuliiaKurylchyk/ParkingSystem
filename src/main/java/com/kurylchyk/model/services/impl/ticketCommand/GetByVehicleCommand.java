package com.kurylchyk.model.services.impl.ticketCommand;

import com.kurylchyk.model.exceptions.NoSuchParkingTicketException;
import com.kurylchyk.model.services.impl.Command;
import com.kurylchyk.model.dao.ParkingTicketDAO;
import com.kurylchyk.model.domain.parkingTicket.ParkingTicket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetByVehicleCommand implements Command<ParkingTicket> {
    private ParkingTicketDAO parkingTicketDAO;
    private String licensePlate;

    private static final Logger logger = LogManager.getLogger(GetByVehicleCommand.class);

    public GetByVehicleCommand(String licensePlate){

        this.licensePlate = licensePlate;
        parkingTicketDAO = new ParkingTicketDAO();
    }

    GetByVehicleCommand(String licensePlate,ParkingTicketDAO parkingTicketDAO){
        this.licensePlate = licensePlate;
        this.parkingTicketDAO = parkingTicketDAO;
    }
    @Override
    public ParkingTicket execute() throws NoSuchParkingTicketException {
        logger.debug("Getting ticket by vehicle information");
      return   parkingTicketDAO.selectByVehicleID(licensePlate).orElseThrow(
              ()->new NoSuchParkingTicketException("No parking ticket with vehicle "+ licensePlate + " was found!"));
    }
}
