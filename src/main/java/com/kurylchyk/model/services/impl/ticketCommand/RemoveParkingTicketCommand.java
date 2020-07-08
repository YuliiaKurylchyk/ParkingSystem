package com.kurylchyk.model.services.impl.ticketCommand;

import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.parkingSlots.SlotStatus;
import com.kurylchyk.model.services.ParkingSlotService;
import com.kurylchyk.model.services.impl.Command;
import com.kurylchyk.model.Payment;
import com.kurylchyk.model.Timer;
import com.kurylchyk.model.dao.ParkingTicketDAO;
import com.kurylchyk.model.parkingTicket.ParkingTicket;
import com.kurylchyk.model.services.impl.ParkingSlotServiceImpl;
import  com.kurylchyk.model.parkingTicket.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class RemoveParkingTicketCommand implements Command<ParkingTicket> {

    private ParkingTicketDAO parkingTicketDAO =  new ParkingTicketDAO();
    private ParkingTicket parkingTicket;

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
        return  parkingTicket;
    }

    private BigDecimal countTheCost(ParkingTicket parkingTicket) {

        Integer pricePerDay = parkingTicket.getParkingSlot().getPrice();
        parkingTicket.setDepartureTime(LocalDateTime.now());

        System.out.println("TimeArrival " + parkingTicket.getArrivalTime());
        System.out.println("TimeLeft " + parkingTicket.getDepartureTime());

        BigDecimal cost = Payment.calculatePrice(Timer.getTotalDays(parkingTicket.getArrivalTime(),
                parkingTicket.getDepartureTime()), pricePerDay);
        return cost;
    }
}
