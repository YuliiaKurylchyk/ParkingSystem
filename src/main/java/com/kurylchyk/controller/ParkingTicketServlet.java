package com.kurylchyk.controller;

import com.kurylchyk.model.customer.Customer;
import com.kurylchyk.model.dao.ParkingSlotDTO;
import com.kurylchyk.model.parkingSlots.ParkingSlot;
import com.kurylchyk.model.parkingSlots.SlotSize;
import com.kurylchyk.model.parkingSlots.SlotStatus;
import com.kurylchyk.model.parkingTicket.ParkingTicket;
import com.kurylchyk.model.services.CustomerService;
import com.kurylchyk.model.services.ParkingSlotService;
import com.kurylchyk.model.services.ParkingTicketService;
import com.kurylchyk.model.services.VehicleService;
import com.kurylchyk.model.services.impl.ServiceFacade;
import com.kurylchyk.model.services.impl.ParkingSlotServiceImpl;
import com.kurylchyk.model.vehicles.Vehicle;
import com.kurylchyk.model.parkingTicket.Status;

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
    private VehicleService vehicleService = new ServiceFacade().forVehicle();
    private CustomerService customerService = new ServiceFacade().forCustomer();
    private ParkingSlotService parkingLotService = new ParkingSlotServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String command = req.getPathInfo();
        System.out.println("In parking ticket servlet " + command);
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
            case "/updateVehicle":
                doUpdateVehicle(req, resp);
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
    }

    protected void doGetTicket(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {


        Integer parkingTicketID = Integer.parseInt(req.getParameter("parkingTicketID"));
        try {
            ParkingTicket parkingTicket = parkingTicketService.getByID(parkingTicketID);
            req.getSession().setAttribute("currentTicket", parkingTicket);
            req.getRequestDispatcher("/parkingTicketInfo.jsp").forward(req, resp);
        } catch (Exception exception) {
            req.setAttribute("NotFound", exception);
            System.out.println("exception in doGetTicket");
            req.getRequestDispatcher("/searchPage.jsp").forward(req, resp);
        }

    }

    protected void doCreate(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        System.out.println("In parking ticket creation");
        Vehicle vehicle = (Vehicle) session.getAttribute("vehicle");
        ParkingSlot parkingSlot = (ParkingSlot) session.getAttribute("parkingSlot");
        Customer customer = (Customer) session.getAttribute("customer");

        ParkingTicket parkingTicket = null;

        try {
            parkingTicket = parkingTicketService.createParkingTicket(vehicle, customer, parkingSlot);
            vehicleService.saveToDB(parkingTicket.getVehicle());
            parkingLotService.updateStatus(parkingSlot, SlotStatus.OCCUPIED);
            customer = customerService.saveToDB(parkingTicket.getCustomer());
            vehicleService.connectCustomerToVehicle(vehicle, customer);
            parkingTicket = parkingTicketService.saveToDB(parkingTicket);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        session.removeAttribute("vehicle");
        session.removeAttribute("customer");
        session.removeAttribute("parkingSlot");
        session.setAttribute("currentTicket", parkingTicket);

        req.getRequestDispatcher("/parkingTicketInfo.jsp").forward(req, resp);
    }

    protected void doShow(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        //зробити шоб  з create ми брали лише ID,а ось тут діставали.
        //і використати тут атрибути а не сесію, або параметр.
        if (req.getParameter("parkingTicketID") != null) {
            try {
                Integer parkingTicketID = Integer.parseInt(req.getParameter("parkingTicketID"));
                ParkingTicket currentTicket = parkingTicketService.getByID(parkingTicketID);
                req.getSession().setAttribute("currentTicket", currentTicket);

            } catch (Exception exception) {
                exception.printStackTrace();
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

        } catch (
                Exception exception) {
            exception.printStackTrace();
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
                    System.out.println("in all and allTickets");
                } else {
                    LocalDateTime dateTime = DateFactory.getAppropriateDate(date);
                    appropriateTickets = parkingTicketService.getAllInDate(dateTime);
                    System.out.println("in all and else");
                }
            } else {
                Status status = Status.valueOf(chosenStatus);
                if (date.equals("allTickets")) {
                    appropriateTickets = parkingTicketService.getAll(status);
                    System.out.println("in else and allTickets");
                    System.out.println(appropriateTickets);
                } else {
                    LocalDateTime dateTime = DateFactory.getAppropriateDate(date);
                    appropriateTickets = parkingTicketService.getAllInDateAndStatus(dateTime, status);
                    System.out.println("in else and else");
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        if (appropriateTickets.isEmpty()) {
            req.setAttribute("NotFound", "No tickets are found!");
        }
        req.setAttribute("appropriateTickets", appropriateTickets);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/showAllTickets.jsp");
        requestDispatcher.forward(req, resp);

    }

    protected void doShowByCustomer(HttpServletRequest req, HttpServletResponse resp) {

        Integer customerID = Integer.parseInt(req.getParameter("customerID"));
        System.out.println("customerID = " + customerID);
        try {
            List<ParkingTicket> appropriateTickets = parkingTicketService.getByCustomer(customerID);
            System.out.println(appropriateTickets);
            req.setAttribute("appropriateTickets", appropriateTickets);
            req.setAttribute("onlyCurrentCustomer", "");
            req.getRequestDispatcher("/showAllTickets.jsp").forward(req, resp);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    protected void doShowBySlot(HttpServletRequest req, HttpServletResponse resp) {

        Integer parkingSlotID = Integer.parseInt(req.getParameter("parkingSlotID"));
        SlotSize slotSize = SlotSize.valueOf(req.getParameter("slotSize"));

        try {
            ParkingTicket parkingTicket = parkingTicketService.getByParkingSlot(new ParkingSlotDTO(slotSize, parkingSlotID));
            req.getSession().setAttribute("currentTicket", parkingTicket);
            req.getRequestDispatcher("/parkingTicketInfo.jsp").forward(req, resp);

        } catch (Exception exception) {
            exception.printStackTrace();
        }


    }


    /*
    protected void doShowByCustomer(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        ParkingTicket currentTicket = (ParkingTicket) req.getSession().getAttribute("currentTicket");
        Customer customer = (Customer)req.getAttribute("customer");
        req.removeAttribute("customer");

        if (currentTicket == null) {
            try {
                List<ParkingTicket> allFoundTickets = parkingTicketService.getByCustomer(customer.getCustomerID());

            req.setAttribute("appropriateTickets", allFoundTickets);
            req.setAttribute("onlyCurrentCustomer", "");
            req.getRequestDispatcher("showAllTickets.jsp").forward(req,resp);
            }catch (Exception exception){
                exception.printStackTrace();
            }
        } else {
            currentTicket.setCustomer(customer);
            req.getSession().setAttribute("currentTicket", currentTicket);
            req.getRequestDispatcher("parkingTicketInfo.jsp").forward(req,resp);
        }
    }
*/
    protected void doShowByVehicle(HttpServletRequest req, HttpServletResponse resp) {
        String vehicleID = req.getParameter("vehicleID");
        try {
            ParkingTicket currentTicket = parkingTicketService.getByVehicle(vehicleID);
            req.getSession().setAttribute("currentTicket", currentTicket);
            req.getRequestDispatcher("/parkingTicketInfo.jsp").forward(req, resp);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    protected void doUpdateVehicle(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Vehicle currentVehicle = (Vehicle) req.getAttribute("currentVehicle");
        Vehicle updatedVehicle = (Vehicle) req.getAttribute("updatedVehicle");
        HttpSession session = req.getSession();

        ParkingTicket currentTicket = null;
        try {
            if (session.getAttribute("currentTicket") == null) {
                currentTicket = parkingTicketService.getByVehicle(currentVehicle.getLicensePlate());
            } else {
                currentTicket = (ParkingTicket) session.getAttribute("currentTicket");
            }

            if (!currentVehicle.getLicensePlate().equals(currentVehicle.getLicensePlate())) {
                currentTicket.setVehicle(updatedVehicle);
                parkingTicketService.update(currentTicket);
            }

            if (currentVehicle.getVehicleType() != updatedVehicle.getVehicleType()) {
                ParkingSlotService parkingLotService = new ParkingSlotServiceImpl();
                //  parkingLotService.setParkingSlotBack(currentTicket.getParkingSlot());
                //currentTicket.setParkingSlot(parkingLotService.getParkingSlot(updatedVehicle.getTypeOfVehicle()));
                parkingTicketService.update(currentTicket);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }


        currentTicket.setVehicle(updatedVehicle);
        session.setAttribute("currentTicket", currentTicket);
        session.removeAttribute("vehicle");
        req.getRequestDispatcher("/parkingTicketInfo.jsp").forward(req, resp);

    }

    protected void doRemove(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        System.out.println("In doRemove");
        Integer parkingTicketID = Integer.parseInt(req.getParameter("parkingTicketID"));
        System.out.println("ParkingTicketID " + parkingTicketID);
        try {
            ParkingTicket parkingTicket = parkingTicketService.getByID(parkingTicketID);
            parkingTicket = parkingTicketService.remove(parkingTicket);
            req.getSession().setAttribute("currentTicket", parkingTicket);

        } catch (Exception exception) {
            exception.printStackTrace();
        }

        req.getRequestDispatcher("/parkingTicketInfo.jsp").forward(req, resp);
    }

    protected void doDeleteCompletely(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        System.out.println("deleting in delete completely action");
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
        System.out.println("current ticket was removed from session");
        req.getRequestDispatcher(req.getContextPath() + "/home").forward(req, resp);
    }


}
