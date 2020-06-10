package com.kurylchyk.controller.action;

import com.kurylchyk.model.parkingTicket.ParkingTicket;
import com.kurylchyk.model.services.ParkingTicketService;
import com.kurylchyk.model.services.impl.BusinessServiceFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;

public class ShowAllAction  implements  Action {
    private ParkingTicketService parkingTicketService = new BusinessServiceFactory().forParkingTicket();
    @Override
    public RequestDispatcher execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String date = request.getParameter("dateOfParkingTicket");
        String status = request.getParameter("statusOfParkingTicket");
        List<ParkingTicket> appropriateTickets = null;
        LocalDateTime dateTime = getAppropriateDate(date);

        if (status.equals("all")) {
            if (date.equals("allTickets")) {
                appropriateTickets = parkingTicketService.getAllTickets();
            } else {
                appropriateTickets = parkingTicketService.getAllInDate(dateTime);
            }
        } else {
            if (date.equals("allTickets")) {
                appropriateTickets = parkingTicketService.getAll(status);
            } else {
                appropriateTickets = parkingTicketService.getAllInDateAndStatus(dateTime, status);
            }
        }

        request.setAttribute("appropriateTickets", appropriateTickets);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("showAllTickets.jsp");
        return requestDispatcher;
       // requestDispatcher.forward(req, resp);
    }

    private LocalDateTime getAppropriateDate(String date) {

        LocalDateTime appropriateDate = null;
        switch (date) {
            case "today":
                appropriateDate = LocalDateTime.now();
                break;
            case "yesterday":
                appropriateDate = LocalDateTime.now().minusDays(1);
                break;
            case "oneWeekAgo":
                appropriateDate = LocalDateTime.now().minusWeeks(1);
                break;
            case "MonthAgo":
                appropriateDate = LocalDateTime.now().minusMonths(1);
                break;
        }
        return appropriateDate;


    }

}
