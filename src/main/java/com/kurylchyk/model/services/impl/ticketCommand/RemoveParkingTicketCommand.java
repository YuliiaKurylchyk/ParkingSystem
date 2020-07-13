package com.kurylchyk.model.services.impl.ticketCommand;

import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.domain.parkingSlots.slotEnum.SlotStatus;
import com.kurylchyk.model.services.ParkingSlotService;
import com.kurylchyk.model.services.impl.Command;
import com.kurylchyk.model.services.Payment;
import com.kurylchyk.model.services.DayCounter;
import com.kurylchyk.model.dao.ParkingTicketDAO;
import com.kurylchyk.model.domain.parkingTicket.ParkingTicket;
import com.kurylchyk.model.services.impl.ParkingSlotServiceImpl;
import com.kurylchyk.model.domain.parkingTicket.ticketEnum.Status;
import com.kurylchyk.model.services.impl.ServiceFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Updating information about parking ticket
 * when vehicle is leaving.
 * Departure time and cost are calculated here
 * and status is set to LEFT
 */

public class RemoveParkingTicketCommand implements Command<ParkingTicket> {

    private ParkingTicketDAO parkingTicketDAO;
    ParkingSlotService parkingSlotService;
    private ParkingTicket parkingTicket;
    private static final Logger logger = LogManager.getLogger(RemoveParkingTicketCommand.class);

    public RemoveParkingTicketCommand(ParkingTicket parkingTicket){

        this.parkingTicket = parkingTicket;
        parkingSlotService = ServiceFacade.forParkingSlot();
        parkingTicketDAO = new ParkingTicketDAO();
    }

    RemoveParkingTicketCommand(ParkingTicket parkingTicket,
                               ParkingTicketDAO parkingTicketDAO, ParkingSlotService parkingSlotService){

        this.parkingTicket = parkingTicket;
        this.parkingTicketDAO = parkingTicketDAO;
        this.parkingSlotService = parkingSlotService;
    }


    @Override
    public ParkingTicket execute() throws ParkingSystemException {

        parkingTicket.setDepartureTime(LocalDateTime.now());
        parkingTicket.setStatus(Status.LEFT);
        parkingTicket.setCost(countTheCost(parkingTicket));
        parkingSlotService.updateStatus(parkingTicket.getParkingSlot(), SlotStatus.VACANT);
        parkingTicketDAO.update(parkingTicket,parkingTicket.getParkingTicketID());
        logger.info("Parking ticket "+parkingTicket.getParkingTicketID() +" was updated with departure time, status and cost");
        return  parkingTicket;
    }

    private BigDecimal countTheCost(ParkingTicket parkingTicket) {

        Integer pricePerDay = parkingTicket.getParkingSlot().getPrice();
        System.out.println("Price per day "+pricePerDay);
        parkingTicket.setDepartureTime(LocalDateTime.now());

        BigDecimal cost = Payment.calculatePrice(DayCounter.getTotalDays(parkingTicket.getArrivalTime(),
                parkingTicket.getDepartureTime()), pricePerDay);
        return cost;
    }
}
