package com.kurylchyk.model.services.impl.parkingSlotCommand;

import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.parkingSlots.ParkingSlot;
import com.kurylchyk.model.parkingSlots.SlotStatus;
import com.kurylchyk.model.parkingTicket.ParkingTicket;
import com.kurylchyk.model.services.ParkingSlotService;
import com.kurylchyk.model.services.ParkingTicketService;
import com.kurylchyk.model.services.impl.Command;
import com.kurylchyk.model.services.impl.ServiceFacade;

public class ChangeParkingSlotCommand implements Command<Void> {


    private ParkingSlot parkingSlot;
    private ParkingTicket parkingTicket;
    private ParkingSlotService parkingSlotService = new ServiceFacade().forParkingSlot();
    private ParkingTicketService parkingTicketService = new ServiceFacade().forParkingTicket();

    public ChangeParkingSlotCommand(ParkingTicket parkingTicket, ParkingSlot parkingSlot) {
        this.parkingTicket = parkingTicket;
        this.parkingSlot = parkingSlot;
    }

    @Override
    public Void execute() throws ParkingSystemException {
        parkingSlotService.updateStatus(parkingTicket.getParkingSlot(), SlotStatus.VACANT);
        parkingSlot = parkingSlotService.updateStatus(parkingSlot, SlotStatus.OCCUPIED);
        parkingTicketService.updateParkingSlot(parkingTicket,parkingSlot);
        return null;
    }
}
