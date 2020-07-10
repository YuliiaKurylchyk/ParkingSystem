package com.kurylchyk.controller;

import com.kurylchyk.model.domain.customer.Customer;
import com.kurylchyk.model.services.impl.parkingSlotDTOs.ParkingSlotDTO;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.domain.parkingSlots.ParkingSlot;
import com.kurylchyk.model.domain.parkingSlots.slotEnum.SlotSize;
import com.kurylchyk.model.domain.parkingTicket.ParkingTicket;
import com.kurylchyk.model.services.ParkingTicketService;
import com.kurylchyk.model.services.impl.ServiceFacade;
import com.kurylchyk.model.domain.vehicles.Vehicle;
import com.kurylchyk.model.domain.parkingTicket.ticketEnum.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.time.LocalDateTime;
import java.util.List;


@WebServlet("/parkingTicket/*")
public class ParkingTicketServlet extends HttpServlet {
    private ParkingTicketService parkingTicketService = new ServiceFacade().forParkingTicket();
    private static final Logger logger = LogManager.getLogger(ParkingTicketServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp){

        String command = req.getPathInfo();
        try {
            switch (command) {
                case "/create":
                    doCreate(req, resp);
                    break;
                case "/get":
                    doGetTicket(req, resp);
                    break;
                case "/show":
                    doShow(req, resp);
                    break;
                case "/showAll":
                    doShowAll(req, resp);
                    break;
                case "/showByCustomer":
                    doShowByCustomer(req, resp);
                    break;
                case "/showByVehicle":
                    doShowByVehicle(req, resp);
                    break;
                case "/showBySlot":
                    doShowBySlot(req, resp);
                    break;
                case "/remove":
                    doRemove(req, resp);
                    break;
                case "/delete":
                    doDeleteCompletely(req, resp);
                    break;
                case "/getReceipt":
                    doGetReceipt(req, resp);
                    break;
                case "/end":
                    removeFromSession(req, resp);
                    break;
            }
        }catch (Exception exception){
            logger.error(exception);
        }
    }

    protected void doGetTicket(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {


        Integer parkingTicketID = Integer.parseInt(req.getParameter("parkingTicketID"));
        try {
            ParkingTicket parkingTicket = parkingTicketService.getByID(parkingTicketID);
            req.getSession().setAttribute("currentTicket", parkingTicket);
            req.getRequestDispatcher("/parkingTicketInfo.jsp").forward(req, resp);

        } catch (ParkingSystemException exception) {
            req.setAttribute("NotFound", exception);
            logger.error(exception);
            req.getRequestDispatcher("/searchPage.jsp").forward(req, resp);
        }

    }

    protected void doCreate(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        Vehicle vehicle = (Vehicle) session.getAttribute("vehicle");
        ParkingSlot parkingSlot = (ParkingSlot) session.getAttribute("parkingSlot");
        Customer customer = (Customer) session.getAttribute("customer");

        ParkingTicket parkingTicket = null;

        try {
            parkingTicket = parkingTicketService.createParkingTicket(vehicle, customer, parkingSlot);
            parkingTicket = parkingTicketService.saveToDB(parkingTicket);

        } catch (ParkingSystemException exception) {
            logger.error(exception);
        }

        session.removeAttribute("vehicle");
        session.removeAttribute("customer");
        session.removeAttribute("parkingSlot");
        session.setAttribute("currentTicket", parkingTicket);

        req.getRequestDispatcher("/parkingTicketInfo.jsp").forward(req, resp);
    }

    protected void doShow(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if (req.getParameter("parkingTicketID") != null) {
            try {
                Integer parkingTicketID = Integer.parseInt(req.getParameter("parkingTicketID"));
                ParkingTicket currentTicket = parkingTicketService.getByID(parkingTicketID);
                req.getSession().setAttribute("currentTicket", currentTicket);

            } catch (ParkingSystemException exception) {
                logger.error(exception);
            }
        }
        req.getRequestDispatcher("/parkingTicketInfo.jsp").forward(req, resp);
    }

    protected void doGetReceipt(HttpServletRequest req, HttpServletResponse resp) {

        ParkingTicket parkingTicket = (ParkingTicket) req.getSession().getAttribute("currentTicket");

        resp.setContentType("text/plain");
        resp.setHeader("Content-Disposition", "attachment;filename=receipt.txt");
        try {
            OutputStream os = resp.getOutputStream();
            String parkingTicketInfo = parkingTicket.toString();
            os.write(parkingTicketInfo.getBytes());

            os.flush();
            os.close();

        } catch (Exception exception) {
           logger.error(exception);
        }

    }


    protected void doShowAll(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {


        String date = req.getParameter("date");
        String chosenStatus = req.getParameter("status");
        List<ParkingTicket> appropriateTickets = null;
        try {
            if (chosenStatus.equalsIgnoreCase("ALL")) {
                if (date.equals("allTickets")) {
                    appropriateTickets = parkingTicketService.getAllTickets();

                } else {
                    LocalDateTime dateTime = DateFactory.getAppropriateDate(date);
                    appropriateTickets = parkingTicketService.getAllInDate(dateTime);

                }
            } else {
                Status status = Status.valueOf(chosenStatus);
                if (date.equals("allTickets")) {
                    appropriateTickets = parkingTicketService.getAll(status);

                } else {
                    LocalDateTime dateTime = DateFactory.getAppropriateDate(date);
                    appropriateTickets = parkingTicketService.getAllInDateAndStatus(dateTime, status);
                }
            }
        } catch (ParkingSystemException exception) {
            logger.error(exception);
        }
        if (appropriateTickets.isEmpty()) {
            req.setAttribute("NotFound", "No tickets are found!");
        }
        req.setAttribute("appropriateTickets", appropriateTickets);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/showAllTickets.jsp");
        requestDispatcher.forward(req, resp);

    }

    protected void doShowByCustomer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Integer customerID = Integer.parseInt(req.getParameter("customerID"));
        try {
            List<ParkingTicket> appropriateTickets = parkingTicketService.getByCustomer(customerID);
            req.setAttribute("appropriateTickets", appropriateTickets);
            req.setAttribute("onlyCurrentCustomer", "");
            req.getRequestDispatcher("/showAllTickets.jsp").forward(req, resp);
        } catch (ParkingSystemException exception) {
           logger.error(exception);
        }
    }

    protected void doShowBySlot(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Integer parkingSlotID = Integer.parseInt(req.getParameter("parkingSlotID"));
        SlotSize slotSize = SlotSize.valueOf(req.getParameter("slotSize"));

        try {
            ParkingTicket parkingTicket = parkingTicketService.getByParkingSlot(new ParkingSlotDTO(slotSize, parkingSlotID));
            req.getSession().setAttribute("currentTicket", parkingTicket);
            req.getRequestDispatcher("/parkingTicketInfo.jsp").forward(req, resp);

        } catch (ParkingSystemException exception) {
            logger.error(exception);
        }


    }

    protected void doShowByVehicle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String vehicleID = req.getParameter("vehicleID");
        try {
            ParkingTicket currentTicket = parkingTicketService.getByVehicle(vehicleID);
            req.getSession().setAttribute("currentTicket", currentTicket);
            req.getRequestDispatcher("/parkingTicketInfo.jsp").forward(req, resp);
        } catch (ParkingSystemException exception) {
            logger.error(exception);
        }
    }

    protected void doRemove(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Integer parkingTicketID = Integer.parseInt(req.getParameter("parkingTicketID"));

        try {
            ParkingTicket parkingTicket = parkingTicketService.getByID(parkingTicketID);
            parkingTicket = parkingTicketService.remove(parkingTicket);
            req.getSession().setAttribute("currentTicket", parkingTicket);

        } catch (ParkingSystemException exception) {
            logger.error(exception);
        }

        req.getRequestDispatcher("/parkingTicketInfo.jsp").forward(req, resp);
    }

    protected void doDeleteCompletely(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {


        Integer parkingTicketID = Integer.parseInt(req.getParameter("parkingTicketID"));

        try {
            ParkingTicket currentTicket = parkingTicketService.getByID(parkingTicketID);
            parkingTicketService.deleteCompletely(currentTicket);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        req.setAttribute("deleted", "This ticket has been deleted successfully");
        req.getRequestDispatcher("/parkingTicketInfo.jsp").forward(req, resp);
    }

    protected void removeFromSession(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().removeAttribute("currentTicket");
        resp.sendRedirect("/home");
    }


}
