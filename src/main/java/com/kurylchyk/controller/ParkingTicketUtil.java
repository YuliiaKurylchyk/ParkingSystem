package com.kurylchyk.controller;

import com.kurylchyk.model.ParkingTicket;
import com.kurylchyk.model.dao.ParkingTicketDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/showTicket")
public class ParkingTicketUtil extends HttpServlet {

    private static ParkingTicketDAO parkingTicketDAO = new ParkingTicketDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String date = req.getParameter("dateOfParkingTicket");
        String status = req.getParameter("statusOfParkingTicket");
        List<ParkingTicket> appropriateTickets = null;
        LocalDateTime dateTime = getAppropriateDate(date);

        if (status.equals("all")) {
            if(date.equals("allTickets")) {
                appropriateTickets = parkingTicketDAO.selectAll();
            }else {
                    appropriateTickets = getAppropriateTickets(dateTime);
            }
        } else{
            if(date.equals("allTickets")){
                appropriateTickets = parkingTicketDAO.selectAll(status);
            }else {
                appropriateTickets = getAppropriateTickets(dateTime, status);
            }
        }

        req.setAttribute("appropriateTickets", appropriateTickets);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("showAllTickets.jsp");
        requestDispatcher.forward(req, resp);
    }

    private List<ParkingTicket> getAppropriateTickets(LocalDateTime date) {

        return parkingTicketDAO.selectInDate(date);

    }

    private List<ParkingTicket> getAppropriateTickets(LocalDateTime date, String status) {
       return parkingTicketDAO.selectInDateAndStatus(date,status);
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
