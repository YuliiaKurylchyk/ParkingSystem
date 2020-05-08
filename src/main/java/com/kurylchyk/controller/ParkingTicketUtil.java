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
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Date;

@WebServlet("/ParkingTicketUtil")
public class ParkingTicketUtil extends HttpServlet {

    private static ParkingTicketDAO parkingTicketDAO = new ParkingTicketDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String date = req.getParameter("dateOfParkingTicket");
        String status  = req.getParameter("statusOfParkingTicket");

        System.out.println(date);
        System.out.println(status);
            if(date!=null) {
             List<ParkingTicket> appropriateTickets =    getAppropriateTickets(date);
                req.setAttribute("appropriateTickets",appropriateTickets);
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("showAllTickets.jsp");
                requestDispatcher.forward(req,resp);
            }
    }

    public static List<ParkingTicket> getAppropriateTickets(String action){
       List list = null;

        switch (action) {
            case "today" :
                list = parkingTicketDAO.selectInDate(LocalDateTime.now());
                break;
            case "yesterday":
                list = parkingTicketDAO.selectInDate(LocalDateTime.now().minusDays(1));
                break;
            case "oneWeekAgo":
                list = parkingTicketDAO.selectInDate(LocalDateTime.now().minusWeeks(1));
                break;
            case"MonthAgo":
                list = parkingTicketDAO.selectInDate(LocalDateTime.now().minusMonths(1));
                break;

        }

        return list;

    }




}
