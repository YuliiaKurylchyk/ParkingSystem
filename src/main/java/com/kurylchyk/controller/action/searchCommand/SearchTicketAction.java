package com.kurylchyk.controller.action.searchCommand;

import com.kurylchyk.model.parkingTicket.ParkingTicket;
import com.kurylchyk.model.services.ParkingTicketService;
import com.kurylchyk.model.services.impl.BusinessServiceFactory;
import com.kurylchyk.model.services.impl.CustomerServiceImpl;

import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SearchTicketAction implements Search {

    private ParkingTicketService parkingTicketService = new BusinessServiceFactory().forParkingTicket();
    private ParkingTicket parkingTicket;

    public RequestDispatcher search(HttpServletRequest req, HttpServletResponse resp) {

        System.out.println("in parkingTicketSearch action!");
        RequestDispatcher requestDispatcher = null;
        String option = req.getParameter("option");
        HttpSession session = req.getSession();
        try {
            switch (option) {
                case "parkingTicket":
                    String parkingTicketID = req.getParameter("parkingTicketID");
                    parkingTicket = parkingTicketService.getByID(Integer.parseInt(parkingTicketID));
                    session.setAttribute("currentTicket", parkingTicket);
                    requestDispatcher = req.getRequestDispatcher("parkingTicketInfo.jsp");
                    System.out.println("found parking ticket");
                    break;
                case "customer":
                    Integer customerID = null;
                    if (req.getParameter("phoneNumber") != null) {
                        customerID = new CustomerServiceImpl()
                                .getFromDB(req.getParameter("phoneNumber"))
                                .getCustomerID();
                    } else if (req.getParameter("customerID") != null) {
                        customerID = Integer.parseInt(req.getParameter("customerID"));
                    }
                    List<ParkingTicket> allTickets = parkingTicketService.getByCustomer(customerID);
                    session.setAttribute("appropriateTickets", allTickets);
                    requestDispatcher = req.getRequestDispatcher("showAllTickets.jsp");
                    System.out.println("found by customer");
                    break;
                case "vehicle":
                    String vehicleID = req.getParameter("vehicleID");
                    parkingTicket = parkingTicketService.getByVehicle(vehicleID);
                    session.setAttribute("currentTicket", parkingTicket);
                    requestDispatcher = req.getRequestDispatcher("parkingTicketInfo.jsp");
                    System.out.println("found by vehicle");
                    break;
            }
        } catch (Exception exception) {
            req.setAttribute("notFound", exception);
            requestDispatcher = req.getRequestDispatcher("searchPage.jsp");
        }

        if (req.getSession().getAttribute("action")!=null && (
                req.getSession().getAttribute("action").equals("delete") ||req.getSession().getAttribute("action").equals("remove"))){
            System.out.println("to delete");
            requestDispatcher = req.getRequestDispatcher("delete");
        }
        return requestDispatcher;
    }


}
