package com.kurylchyk.controller.action.deleteCommand;

import com.kurylchyk.controller.action.Action;
import com.kurylchyk.model.parkingTicket.ParkingTicket;
import com.kurylchyk.model.services.ParkingTicketService;
import com.kurylchyk.model.services.impl.BusinessServiceFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteCompletelyAction implements Action {
    private ParkingTicketService parkingTicketService = new BusinessServiceFactory().forParkingTicket();

    @Override
    public RequestDispatcher execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        System.out.println("deleting in delete completely action");
        ParkingTicket currentTicket = (ParkingTicket) request.getSession().getAttribute("currentTicket");
        parkingTicketService.deleteCompletely(currentTicket);
        request.setAttribute("deleted", "This ticket has been deleted successfully");
        return request.getRequestDispatcher("parkingTicketInfo.jsp");
    }
}
