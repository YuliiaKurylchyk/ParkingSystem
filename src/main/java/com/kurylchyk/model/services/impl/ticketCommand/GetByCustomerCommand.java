package com.kurylchyk.model.services.impl.ticketCommand;

import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.services.impl.Command;
import com.kurylchyk.model.customer.Customer;
import com.kurylchyk.model.dao.ParkingTicketDAO;
import com.kurylchyk.model.parkingTicket.ParkingTicket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class GetByCustomerCommand implements Command<List<ParkingTicket>> {

    private ParkingTicketDAO parkingTicketDAO = new ParkingTicketDAO();
    private Integer customerID;
    private static final Logger logger = LogManager.getLogger(GetByCustomerCommand.class);

    public GetByCustomerCommand(Integer customerID){
        this.customerID = customerID;
    }

    @Override
    public List<ParkingTicket> execute() throws ParkingSystemException {
        logger.debug("Getting tickets by customer id " + customerID);
        return  parkingTicketDAO.selectByCustomerID(customerID);
    }
}
