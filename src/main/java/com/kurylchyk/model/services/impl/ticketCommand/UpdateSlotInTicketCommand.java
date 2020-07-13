package com.kurylchyk.model.services.impl.ticketCommand;

import com.kurylchyk.model.dao.ParkingTicketDAO;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.domain.parkingSlots.ParkingSlot;
import com.kurylchyk.model.domain.parkingTicket.ParkingTicket;
import com.kurylchyk.model.services.impl.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Updates slot information in parking ticket
 */
public class UpdateSlotInTicketCommand  implements Command<Void> {

    private ParkingTicket parkingTicket;
    private ParkingSlot parkingSlot;
    private ParkingTicketDAO parkingTicketDAO;
    private static final Logger logger = LogManager.getLogger(UpdateSlotInTicketCommand.class);


    public UpdateSlotInTicketCommand(ParkingTicket parkingTicket,ParkingSlot parkingSlot){
        this.parkingTicket = parkingTicket;
        this.parkingSlot = parkingSlot;
        parkingTicketDAO = new ParkingTicketDAO();
    }

    UpdateSlotInTicketCommand(ParkingTicket parkingTicket,ParkingSlot parkingSlot,ParkingTicketDAO parkingTicketDAO){
        this.parkingTicket = parkingTicket;
        this.parkingSlot = parkingSlot;
        this.parkingTicketDAO = parkingTicketDAO;
    }

    @Override
    public Void execute() throws ParkingSystemException {
        parkingTicketDAO.updateParkingSlotID(parkingTicket,parkingSlot);
        logger.info("Parking ticket "+parkingTicket.getParkingTicketID() + " was updated with parking slot "+parkingSlot);
        return null;
    }
}
