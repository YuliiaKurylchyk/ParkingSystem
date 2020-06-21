package com.kurylchyk.controller;

import com.kurylchyk.model.parkingTicket.ParkingTicket;
import com.kurylchyk.model.services.ParkingTicketService;
import com.kurylchyk.model.services.impl.BusinessServiceFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/showTicket")
public class ShowTicketServlet extends HttpServlet {

   private ParkingTicketService parkingTicketService = new BusinessServiceFactory().forParkingTicket();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
       doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
/*
        String date = req.getParameter("date");
        String status = req.getParameter("status");
        List<ParkingTicket> appropriateTickets = null;
        LocalDateTime dateTime = getAppropriateDate(date);

        if (status.equals("all")) {
            if(date.equals("allTickets")) {
                try {
                    appropriateTickets = parkingTicketService.getAllTickets();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }else {
                try {
                    appropriateTickets = getAppropriateTickets(dateTime);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        } else{
            if(date.equals("allTickets")){
                try {
                    appropriateTickets = parkingTicketService.getAll(status);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }else {
                try {
                    appropriateTickets = getAppropriateTickets(dateTime, status);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }

        req.setAttribute("appropriateTickets", appropriateTickets);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("showAllTickets.jsp");
        requestDispatcher.forward(req, resp);

 */
    }
    private List<ParkingTicket> getAppropriateTickets(LocalDateTime date) throws Exception {
        return parkingTicketService.getAllInDate(date);

    }
    private List<ParkingTicket> getAppropriateTickets(LocalDateTime date, String status) throws Exception {
       return null;//parkingTicketService.getAllInDateAndStatus(date,status);
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
            case "monthAgo":
                appropriateDate = LocalDateTime.now().minusMonths(1);
                break;

        }
        return appropriateDate;
    }

}
