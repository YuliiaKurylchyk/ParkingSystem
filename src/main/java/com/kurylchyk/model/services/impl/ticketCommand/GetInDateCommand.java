package com.kurylchyk.model.services.impl.ticketCommand;

import com.kurylchyk.model.dao.ParkingTicketDAO;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.domain.parkingTicket.ParkingTicket;
import com.kurylchyk.model.services.impl.Command;
import com.kurylchyk.model.domain.parkingTicket.ticketEnum.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.time.LocalDateTime;
import java.util.List;

public class GetInDateCommand implements Command<List<ParkingTicket>> {
    private ParkingTicketDAO parkingTicketDAO = new ParkingTicketDAO();
    private LocalDateTime localDateTime;
    private Status status;
    private static final Logger logger = LogManager.getLogger(GetInDateCommand.class);
    public GetInDateCommand(LocalDateTime localDateTime){
        this.localDateTime = localDateTime;
    }

    public GetInDateCommand(LocalDateTime localDateTime,Status status){
        this.localDateTime  = localDateTime;
        this.status = status;
    }

    GetInDateCommand(LocalDateTime localDateTime,Status status,ParkingTicketDAO parkingTicketDAO){
        this.localDateTime  = localDateTime;
        this.status = status;
        this.parkingTicketDAO = parkingTicketDAO;
    }


    @Override
    public List<ParkingTicket> execute() throws ParkingSystemException {
        List<ParkingTicket> allTickets;
        if(status!=null){
            allTickets = parkingTicketDAO.selectInDateAndStatus(localDateTime,status);
            logger.debug("Getting tickets using date and status");
        }else
        {
            allTickets = parkingTicketDAO.selectInDate(localDateTime);
            logger.debug("Getting tickets using date only");
        }
        return allTickets;
    }
}
