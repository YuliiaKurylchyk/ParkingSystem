package com.kurylchyk.model.services.impl.ticketCommand;

import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.services.impl.Command;
import com.kurylchyk.model.dao.ParkingTicketDAO;

public class TicketIsPresentCommand  implements Command<Boolean> {
    private ParkingTicketDAO parkingTicketDAO = new ParkingTicketDAO();
    private  Integer parkingTicketID;

    public TicketIsPresentCommand(Integer parkingTicketID){
        this.parkingTicketID   = parkingTicketID;
    }


    @Override
    public Boolean execute() throws ParkingSystemException {
       return parkingTicketDAO.isPresent(parkingTicketID);
    }
}
