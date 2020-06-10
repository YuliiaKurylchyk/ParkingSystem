package com.kurylchyk.controller.action.deleteCommand;

import com.kurylchyk.controller.action.Action;
import com.kurylchyk.model.parkingTicket.ParkingTicket;
import com.kurylchyk.model.services.ParkingTicketService;
import com.kurylchyk.model.services.impl.BusinessServiceFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RemoveAction  implements Action {
    private ParkingTicketService service = new BusinessServiceFactory().forParkingTicket();
    @Override
    public RequestDispatcher execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        ParkingTicket parkingTicket = (ParkingTicket) session.getAttribute("currentTicket");
        if(parkingTicket.getStatus().equals("left")){
            request.setAttribute("alreadyLeft","The parking ticket status is already left");
        }else {
            parkingTicket = service.remove(parkingTicket);
        }
        session.setAttribute("currentTicket", parkingTicket);
       return request.getRequestDispatcher("parkingTicketInfo.jsp");
    }
}
