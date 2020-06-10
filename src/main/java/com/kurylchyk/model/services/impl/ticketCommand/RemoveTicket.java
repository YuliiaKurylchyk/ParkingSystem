package com.kurylchyk.model.services.impl.ticketCommand;

import com.kurylchyk.model.services.ParkingLotService;
import com.kurylchyk.model.services.impl.Command;
import com.kurylchyk.model.Payment;
import com.kurylchyk.model.TimeCheck;
import com.kurylchyk.model.dao.ParkingTicketDAO;
import com.kurylchyk.model.parkingTicket.ParkingTicket;
import com.kurylchyk.model.services.impl.ParkingLotServiceImpl;
import  com.kurylchyk.model.parkingTicket.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class RemoveTicket implements Command<ParkingTicket> {

    private ParkingTicketDAO parkingTicketDAO =  new ParkingTicketDAO();
    private ParkingTicket parkingTicket;

    public  RemoveTicket(ParkingTicket parkingTicket){
        this.parkingTicket = parkingTicket;
    }


    @Override
    public ParkingTicket execute() throws Exception {
        ParkingLotService parkingLotService = new ParkingLotServiceImpl();
        LocalDateTime leftTime = TimeCheck.getTime();
        parkingTicket.setLeftTime(leftTime);
        parkingTicket.setStatus(Status.LEFT);
        parkingTicket.setCost(countTheCost(parkingTicket));
        parkingLotService.setParkingSlotBack(parkingTicket.getParkingSlot());
        parkingTicketDAO.update(parkingTicket,parkingTicket.getParkingTicketID());
        return  parkingTicket;
    }

    private BigDecimal countTheCost(ParkingTicket parkingTicket) {

        Integer pricePerDay = parkingTicket.getParkingSlot().getPrice();
        System.out.println("TimeArrival " + parkingTicket.getArrivalTime());
        System.out.println("TimeLeft " + parkingTicket.getLeftTime());
        BigDecimal cost = Payment.calculatePrice(TimeCheck.countOfDays(parkingTicket.getArrivalTime(),
                parkingTicket.getLeftTime()), pricePerDay);
        return cost;
    }
}
