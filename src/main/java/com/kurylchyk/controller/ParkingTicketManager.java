package com.kurylchyk.controller;

import com.kurylchyk.model.Customer;
import com.kurylchyk.model.ParkingTicket;
import com.kurylchyk.model.Payment;
import com.kurylchyk.model.TimeCheck;
import com.kurylchyk.model.dao.CustomerDao;
import com.kurylchyk.model.dao.ParkingSlotDao;
import com.kurylchyk.model.dao.ParkingTicketDAO;
import com.kurylchyk.model.dao.VehicleDao;
import com.kurylchyk.model.exceptions.NoSuchCustomerFoundException;
import com.kurylchyk.model.exceptions.NoSuchParkingTicketException;
import com.kurylchyk.model.exceptions.NoSuchVehicleFoundException;
import com.kurylchyk.model.vehicles.Vehicle;
import com.sun.deploy.net.HttpResponse;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public final class ParkingTicketManager {
    private static ParkingTicketDAO parkingTicketDAO = new ParkingTicketDAO();
    private static ParkingSlotDao parkingSlotDao = new ParkingSlotDao();
    private static CustomerDao customerDao = new CustomerDao();
    private static VehicleDao vehicleDao = new VehicleDao();

    public static void removeParkingTicket(ParkingTicket parkingTicket) {
        LocalDateTime leftTime = TimeCheck.getTime();
        parkingTicket.setLeftTime(leftTime);
        parkingTicket.setStatus("left");
        parkingTicket.setCost(countTheCost(parkingTicket));
        parkingTicketDAO.update(parkingTicket,parkingTicket.getParkingTicketID());
    }

    private static BigDecimal countTheCost(ParkingTicket parkingTicket) {

        Integer pricePerDay = parkingSlotDao.selectPrice(parkingTicket.getParkingSlot().getSizeOfSlot());
        System.out.println("TimeArrival " + parkingTicket.getArrivalTime());
        System.out.println("TimeLeft " + parkingTicket.getLeftTime());
        BigDecimal cost = Payment.calculatePrice(TimeCheck.countOfDays(parkingTicket.getArrivalTime(),
                parkingTicket.getLeftTime()), pricePerDay);
        return cost;
    }

    public static ParkingTicket searchParkingTicket(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer parkingTicketID = Integer.parseInt(req.getParameter("id"));
        RequestDispatcher requestDispatcher = null;
        ParkingTicket parkingTicket = null;

        try {
          parkingTicket = parkingTicketDAO.select(parkingTicketID);

        } catch (NoSuchParkingTicketException exception) {
            req.setAttribute("notFound", exception);
            requestDispatcher = req.getRequestDispatcher("searchPage.jsp");
        }
        return parkingTicket;

    }

    public static Customer searchCustomerByPhoneNumber(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String customerPhoneNumber = req.getParameter("phone_number");
        Customer customer = null;
        RequestDispatcher requestDispatcher = null;
        try {
            customer = customerDao.select(customerDao.selectIdByPhoneNumber(customerPhoneNumber));
        } catch (NoSuchCustomerFoundException exception) {
            req.setAttribute("notFound", exception);
            requestDispatcher = req.getRequestDispatcher("searchPage.jsp");
            requestDispatcher.forward(req, resp);
        }
        return customer;
    }

    public static Customer searchCustomerByID(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Integer id = Integer.parseInt(req.getParameter("customerID"));
        RequestDispatcher requestDispatcher = null;
        Customer customer = null;
        try {
            customer = customerDao.select(id);
        } catch (NoSuchCustomerFoundException exception) {
            req.setAttribute("notFound", exception);
            requestDispatcher = req.getRequestDispatcher("searchPage.jsp");
            requestDispatcher.forward(req, resp);
        }
        return customer;

    }

    public static void checkAction(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        RequestDispatcher requestDispatcher;
        if(session.getAttribute("currentTicket")==null) {
            ParkingTicket currentTicket = null;
            if(session.getAttribute("vehicle")!=null){
                currentTicket =ParkingTicketManager.getTicketByVehicle((Vehicle)session.getAttribute("vehicle"));
                session.removeAttribute("vehicle");
            }
            if(session.getAttribute("customer")!=null) {
                currentTicket = ParkingTicketManager.getTicketByCustomer((Customer)session.getAttribute("customer"));
                session.removeAttribute("customer");
            }
            session.setAttribute("currentTicket",currentTicket);
        }
        requestDispatcher = req.getRequestDispatcher("parkingTicketInfo.jsp");
        requestDispatcher.forward(req,resp);

    }

    public static Vehicle searchVehicle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String licencePlate = req.getParameter("licence_plate");
        Vehicle vehicle = null;
        RequestDispatcher requestDispatcher = null;
        try {
            vehicle = vehicleDao.select(licencePlate);
        } catch (NoSuchVehicleFoundException exception) {
            req.setAttribute("notFound", exception);
            requestDispatcher = req.getRequestDispatcher("searchPage.jsp");
        }
        return vehicle;
    }

    public static ParkingTicket getTicketByVehicle(Vehicle vehicle) {
        return parkingTicketDAO.selectByVehicleID(vehicle.getLicencePlate());
    }

    public static ParkingTicket getTicketByCustomer(Customer customer) {
        return parkingTicketDAO.selectByCustomerID(customer.getCustomerID());
    }

}
