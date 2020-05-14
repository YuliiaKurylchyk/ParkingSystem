package com.kurylchyk.controller;

import com.kurylchyk.model.*;
import com.kurylchyk.model.dao.CustomerDao;
import com.kurylchyk.model.dao.ParkingSlotDao;
import com.kurylchyk.model.dao.ParkingTicketDAO;
import com.kurylchyk.model.dao.VehicleDao;
import com.kurylchyk.model.exceptions.*;
import com.kurylchyk.model.parkingSlots.ParkingSlot;
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
        ParkingLot.setParkingSlotBack(parkingTicket.getParkingSlot());
        parkingTicketDAO.update(parkingTicket,parkingTicket.getParkingTicketID());
    }

    public static  ParkingSlot getParkingSlot(HttpServletRequest req, HttpServletResponse resp, Vehicle vehicle) throws ServletException, IOException {
        ParkingSlot parkingSlot = null;
        try {
            parkingSlot = ParkingLot.getParkingSlot(vehicle);
        } catch (NoAvailableParkingSlotException exception){
            req.setAttribute("NoSlots", exception);
            req.getRequestDispatcher("NoPlateFoundError.jsp").forward(req, resp);
        }

        return parkingSlot;
    }

    public static void setParkingSlotBack(ParkingSlot parkingSlot){
        ParkingLot.setParkingSlotBack(parkingSlot);
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

    public static ParkingTicket getTicketByID(Integer id) {
        try {
            return parkingTicketDAO.select(id);
        }catch (NoSuchParkingTicketException ex){
            ex.printStackTrace();
        }
        return null;
    }
}
