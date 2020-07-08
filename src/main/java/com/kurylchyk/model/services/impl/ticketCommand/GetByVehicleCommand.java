package com.kurylchyk.model.services.impl.ticketCommand;

import com.kurylchyk.model.exceptions.NoSuchParkingTicketException;
import com.kurylchyk.model.services.impl.Command;
import com.kurylchyk.model.dao.ParkingTicketDAO;
import com.kurylchyk.model.parkingTicket.ParkingTicket;

public class GetByVehicleCommand implements Command<ParkingTicket> {
    private ParkingTicketDAO parkingTicketDAO = new ParkingTicketDAO();
    private String licensePlate;

    public GetByVehicleCommand(String licensePlate){
        this.licensePlate = licensePlate;
    }


    @Override
    public ParkingTicket execute() throws NoSuchParkingTicketException {
      return   parkingTicketDAO.selectByVehicleID(licensePlate).orElseThrow(
              ()->new NoSuchParkingTicketException("No parking ticket with vehicle "+ licensePlate + " was found!"));
    }
}
