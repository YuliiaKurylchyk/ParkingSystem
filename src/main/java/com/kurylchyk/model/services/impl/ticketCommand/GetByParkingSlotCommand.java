package com.kurylchyk.model.services.impl.ticketCommand;
import com.kurylchyk.model.dao.ParkingSlotIdentifier;
import com.kurylchyk.model.dao.ParkingTicketDAO;
import com.kurylchyk.model.parkingTicket.ParkingTicket;
import com.kurylchyk.model.services.impl.Command;

public class GetByParkingSlotCommand implements Command<ParkingTicket> {

    private ParkingSlotIdentifier identifier;
    private ParkingTicketDAO parkingTicketDAO = new ParkingTicketDAO();

    public GetByParkingSlotCommand(ParkingSlotIdentifier identifier) {
        this.identifier = identifier;
    }

    @Override
    public ParkingTicket execute() throws Exception {

        return parkingTicketDAO.selectByParkingSlot(identifier).get();
    }
}
