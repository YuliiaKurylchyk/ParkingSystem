package com.kurylchyk.model.services.impl.ticketCommand;

import com.kurylchyk.model.dao.ParkingTicketDAO;
import com.kurylchyk.model.parkingTicket.ParkingTicket;
import com.kurylchyk.model.services.impl.Command;
import com.kurylchyk.model.parkingTicket.Status;
import java.util.List;

public class GetAllTicketCommand  implements Command<List<ParkingTicket>> {
    private ParkingTicketDAO parkingTicketDAO = new ParkingTicketDAO();
    private Status status;
    public GetAllTicketCommand(){ }
    public GetAllTicketCommand(Status status){
        this.status = status;
    }

    @Override
    public List<ParkingTicket> execute() throws Exception {
        List<ParkingTicket> allTickets;
        if(status!=null){
            allTickets = parkingTicketDAO.selectAll(status);
        }else
        {
            allTickets = parkingTicketDAO.selectAll();
        }
        return allTickets;
    }
}
