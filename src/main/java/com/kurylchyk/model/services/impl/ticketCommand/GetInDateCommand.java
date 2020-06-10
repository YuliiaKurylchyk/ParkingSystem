package com.kurylchyk.model.services.impl.ticketCommand;

import com.kurylchyk.model.dao.ParkingTicketDAO;
import com.kurylchyk.model.parkingTicket.ParkingTicket;
import com.kurylchyk.model.services.impl.Command;

import java.time.LocalDateTime;
import java.util.List;

public class GetInDateCommand implements Command<List<ParkingTicket>> {
    private ParkingTicketDAO parkingTicketDAO = new ParkingTicketDAO();
    private LocalDateTime localDateTime;
    private String status;
    public GetInDateCommand(LocalDateTime localDateTime){
        this.localDateTime = localDateTime;
    }
    public GetInDateCommand(LocalDateTime localDateTime,String status){
        this.localDateTime  = localDateTime;
        this.status = status;
    }

    @Override
    public List<ParkingTicket> execute() throws Exception {
        List<ParkingTicket> allTickets;
        if(status!=null){
            allTickets = parkingTicketDAO.selectInDateAndStatus(localDateTime,status);
        }else
        {
            allTickets = parkingTicketDAO.selectInDate(localDateTime);
        }
        return allTickets;
    }
}
