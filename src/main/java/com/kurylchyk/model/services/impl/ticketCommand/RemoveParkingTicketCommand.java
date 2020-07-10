package com.kurylchyk.model.services.impl.ticketCommand;

import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.domain.parkingSlots.slotEnum.SlotStatus;
import com.kurylchyk.model.services.ParkingSlotService;
import com.kurylchyk.model.services.impl.Command;
import com.kurylchyk.model.services.Payment;
import com.kurylchyk.model.services.TimeChecker;
import com.kurylchyk.model.dao.ParkingTicketDAO;
import com.kurylchyk.model.domain.parkingTicket.ParkingTicket;
import com.kurylchyk.model.services.impl.ParkingSlotServiceImpl;
import com.kurylchyk.model.domain.parkingTicket.ticketEnum.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class RemoveParkingTicketCommand implements Command<ParkingTicket> {

    private ParkingTicketDAO parkingTicketDAO =  new ParkingTicketDAO();
    private ParkingTicket parkingTicket;
    private static final Logger logger = LogManager.getLogger(RemoveParkingTicketCommand.class);

    public RemoveParkingTicketCommand(ParkingTicket parkingTicket){
        this.parkingTicket = parkingTicket;
    }


    @Override
    public ParkingTicket execute() throws ParkingSystemException {
        ParkingSlotService parkingLotService = new ParkingSlotServiceImpl();

        parkingTicket.setDepartureTime(LocalDateTime.now());
        parkingTicket.setStatus(Status.LEFT);
        parkingTicket.setCost(countTheCost(parkingTicket));
        parkingLotService.updateStatus(parkingTicket.getParkingSlot(), SlotStatus.VACANT);
        parkingTicketDAO.update(parkingTicket,parkingTicket.getParkingTicketID());
        logger.info("Parking ticket "+parkingTicket.getParkingTicketID() +" was updated with departure time, status and cost");
        return  parkingTicket;
    }

    private BigDecimal countTheCost(ParkingTicket parkingTicket) {

        Integer pricePerDay = parkingTicket.getParkingSlot().getPrice();
        System.out.println("Price per day "+pricePerDay);
        parkingTicket.setDepartureTime(LocalDateTime.now());

        System.out.println("TimeArrival " + parkingTicket.getArrivalTime());
        System.out.println("TimeLeft " + parkingTicket.getDepartureTime());

        BigDecimal cost = Payment.calculatePrice(TimeChecker.getTotalDays(parkingTicket.getArrivalTime(),
                parkingTicket.getDepartureTime()), pricePerDay);
        return cost;
    }
}
