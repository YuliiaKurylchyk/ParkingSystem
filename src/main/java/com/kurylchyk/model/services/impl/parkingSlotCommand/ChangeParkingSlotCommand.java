package com.kurylchyk.model.services.impl.parkingSlotCommand;

import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.domain.parkingSlots.ParkingSlot;
import com.kurylchyk.model.domain.parkingSlots.slotEnum.SlotStatus;
import com.kurylchyk.model.domain.parkingTicket.ParkingTicket;
import com.kurylchyk.model.services.ParkingSlotService;
import com.kurylchyk.model.services.ParkingTicketService;
import com.kurylchyk.model.services.impl.Command;
import com.kurylchyk.model.services.impl.ServiceFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChangeParkingSlotCommand implements Command<Void> {


    private ParkingSlot parkingSlot;
    private ParkingTicket parkingTicket;
    private ParkingSlotService parkingSlotService = new ServiceFacade().forParkingSlot();
    private ParkingTicketService parkingTicketService = new ServiceFacade().forParkingTicket();
    private Logger logger = LogManager.getLogger(ChangeParkingSlotCommand.class);

    public ChangeParkingSlotCommand(ParkingTicket parkingTicket, ParkingSlot parkingSlot) {
        this.parkingTicket = parkingTicket;
        this.parkingSlot = parkingSlot;
    }

    @Override
    public Void execute() throws ParkingSystemException {
        parkingSlotService.updateStatus(parkingTicket.getParkingSlot(), SlotStatus.VACANT);
        parkingSlot = parkingSlotService.updateStatus(parkingSlot, SlotStatus.OCCUPIED);
        parkingTicketService.updateParkingSlot(parkingTicket,parkingSlot);
        logger.info("New parking slot "+parkingSlot + " was set to parking ticket "+parkingTicket.getParkingTicketID());
        return null;
    }
}
