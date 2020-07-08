package com.kurylchyk.model.services.impl.ticketCommand;

import com.kurylchyk.model.dao.ParkingTicketDAO;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.parkingSlots.ParkingSlot;
import com.kurylchyk.model.parkingTicket.ParkingTicket;
import com.kurylchyk.model.services.impl.Command;

public class UpdateSlotInTicketCommand  implements Command<Void> {

    private ParkingTicket parkingTicket;
    private ParkingSlot parkingSlot;
    private ParkingTicketDAO parkingTicketDAO = new ParkingTicketDAO();


    public UpdateSlotInTicketCommand(ParkingTicket parkingTicket,ParkingSlot parkingSlot){
        this.parkingTicket = parkingTicket;
        this.parkingSlot = parkingSlot;
    }

    @Override
    public Void execute() throws ParkingSystemException {
        parkingTicketDAO.updateParkingSlotID(parkingTicket,parkingSlot);
        return null;
    }
}
