package com.kurylchyk.controller;

import com.kurylchyk.model.*;
import com.kurylchyk.model.customer.Customer;
import com.kurylchyk.model.dao.CustomerDAO;
import com.kurylchyk.model.dao.ParkingSlotDAO;
import com.kurylchyk.model.dao.ParkingTicketDAO;
import com.kurylchyk.model.dao.VehicleDAO;
import com.kurylchyk.model.exceptions.*;
import com.kurylchyk.model.parkingSlots.ParkingSlot;
import com.kurylchyk.model.parkingTicket.ParkingTicket;
import com.kurylchyk.model.service.CustomerService;
import com.kurylchyk.model.service.ParkingTicketService;
import com.kurylchyk.model.service.VehicleService;
import com.kurylchyk.model.vehicles.Vehicle;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public final class ParkingTicketManager {
    private static ParkingTicketService parkingTicketService = new ParkingTicketService();
    private static ParkingSlotDAO parkingSlotDao = new ParkingSlotDAO();
    private static CustomerService customerService = new CustomerService();
    private static VehicleService vehicleService = new VehicleService();

    public static void removeParkingTicket(ParkingTicket parkingTicket) {
        LocalDateTime leftTime = TimeCheck.getTime();
        parkingTicket.setLeftTime(leftTime);
        parkingTicket.setStatus("left");
        parkingTicket.setCost(countTheCost(parkingTicket));
        ParkingLot.setParkingSlotBack(parkingTicket.getParkingSlot());
        parkingTicketService.update(parkingTicket,parkingTicket.getParkingTicketID());
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

    //remove (first part only)
    public static ParkingTicket searchParkingTicket(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Integer parkingTicketID = Integer.parseInt(req.getParameter("id"));
        RequestDispatcher requestDispatcher = null;
        ParkingTicket parkingTicket = null;

        try {
            parkingTicket = parkingTicketService.get(parkingTicketID);

        } catch (NoSuchParkingTicketException exception) {
            req.setAttribute("notFound", exception);
            req.getRequestDispatcher("searchPage.jsp").forward(req,resp);
        }
        return parkingTicket;

    }

    //remove
    public static Customer searchCustomerByPhoneNumber(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String customerPhoneNumber = req.getParameter("phone_number");
        Customer customer = null;
        RequestDispatcher requestDispatcher = null;
        try {
            customer = customerService.get(customerPhoneNumber);
        } catch (NoSuchCustomerFoundException exception) {
            req.setAttribute("notFound", exception);
            requestDispatcher = req.getRequestDispatcher("searchPage.jsp");
            requestDispatcher.forward(req, resp);
        }
        return customer;
    }

//remove
    public static Vehicle searchVehicle(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String licencePlate = req.getParameter("licence_plate");

        Vehicle vehicle = null;
        RequestDispatcher requestDispatcher = null;
        try {
            vehicle = vehicleService.get(licencePlate);
        } catch (NoSuchVehicleFoundException exception) {
            req.setAttribute("notFound", exception);
             req.getRequestDispatcher("searchPage.jsp").forward(req,resp);
        }
        return vehicle;
    }
//remove
    public static ParkingTicket getTicketByVehicle(Vehicle vehicle) throws Exception {
            return parkingTicketService.getByVehicle(vehicle.getLicencePlate());
    }
    //remove
    public static ParkingTicket getTicketByCustomer(Customer customer) throws Exception {
        return parkingTicketService.getByCustomer(customer.getCustomerID()).get(0);
    }

    //remove
    public static ParkingTicket getTicketByID(Integer id) {
      //  try {
        //    return parkingTicketDAO.select(id);
        //}catch (NoSuchParkingTicketException ex){
          //  ex.printStackTrace();
        //}
        return null;
    }

    public static void deleteCompletely(ParkingTicket currentTicket) {
        Integer countOfCustomers = customerService.countCustomersVehicle(currentTicket.getCustomer().getCustomerID());
        parkingTicketService.delete(currentTicket);
        vehicleService.delete(currentTicket.getVehicle());
        if(countOfCustomers==1) {
            customerService.delete(currentTicket.getCustomer());
        }
    }
}
