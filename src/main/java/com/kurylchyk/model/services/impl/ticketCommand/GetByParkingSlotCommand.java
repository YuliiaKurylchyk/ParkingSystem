package com.kurylchyk.model.services.impl.ticketCommand;
import com.kurylchyk.model.services.impl.parkingSlotDTO.ParkingSlotDTO;
import com.kurylchyk.model.dao.ParkingTicketDAO;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.domain.parkingTicket.ParkingTicket;
import com.kurylchyk.model.services.impl.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetByParkingSlotCommand implements Command<ParkingTicket> {

    private ParkingSlotDTO parkingSlotDTO;
    private ParkingTicketDAO parkingTicketDAO;
    private static final Logger logger = LogManager.getLogger(GetByParkingSlotCommand.class);

    public GetByParkingSlotCommand(ParkingSlotDTO identifier) {

        this.parkingSlotDTO = identifier;
        parkingTicketDAO = new ParkingTicketDAO();
    }

    GetByParkingSlotCommand(ParkingSlotDTO parkingSlotDTO,ParkingTicketDAO parkingTicketDAO) {

        this.parkingSlotDTO = parkingSlotDTO;
        this.parkingTicketDAO = parkingTicketDAO;
    }

    @Override
    public ParkingTicket execute() throws ParkingSystemException {

        logger.debug("Getting ticket by parking slot information");
        return parkingTicketDAO.selectByParkingSlot(parkingSlotDTO).get();
    }
}
