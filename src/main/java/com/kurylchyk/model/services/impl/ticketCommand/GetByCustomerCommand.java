package com.kurylchyk.model.services.impl.ticketCommand;

import com.kurylchyk.model.services.impl.Command;
import com.kurylchyk.model.customer.Customer;
import com.kurylchyk.model.dao.ParkingTicketDAO;
import com.kurylchyk.model.parkingTicket.ParkingTicket;

import java.util.List;

public class GetByCustomerCommand implements Command<List<ParkingTicket>> {

    private ParkingTicketDAO parkingTicketDAO = new ParkingTicketDAO();
    private Integer customerID;

    public GetByCustomerCommand(Integer customerID){
        this.customerID = customerID;
    }


    //or else throw
    @Override
    public List<ParkingTicket> execute() throws Exception {
        return  parkingTicketDAO.selectByCustomerID(customerID);
    }
}
