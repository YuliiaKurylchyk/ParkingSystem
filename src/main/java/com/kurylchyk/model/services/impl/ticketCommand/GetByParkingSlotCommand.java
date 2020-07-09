package com.kurylchyk.model.services.impl.ticketCommand;
import com.kurylchyk.model.dao.ParkingSlotDTO;
import com.kurylchyk.model.dao.ParkingTicketDAO;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.parkingTicket.ParkingTicket;
import com.kurylchyk.model.services.impl.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetByParkingSlotCommand implements Command<ParkingTicket> {

    private ParkingSlotDTO identifier;
    private ParkingTicketDAO parkingTicketDAO = new ParkingTicketDAO();
    private static final Logger logger = LogManager.getLogger(GetByParkingSlotCommand.class);

    public GetByParkingSlotCommand(ParkingSlotDTO identifier) {
        this.identifier = identifier;
    }

    @Override
    public ParkingTicket execute() throws ParkingSystemException {

        logger.debug("Getting ticket by parking slot information");
        return parkingTicketDAO.selectByParkingSlot(identifier).get();
    }
}
