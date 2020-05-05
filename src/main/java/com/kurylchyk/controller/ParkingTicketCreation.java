package com.kurylchyk.controller;

import com.kurylchyk.model.Customer;
import com.kurylchyk.model.ParkingTicket;
import com.kurylchyk.model.dao.ParkingTicketDAO;
import com.kurylchyk.model.dao.VehicleDao;
import com.kurylchyk.model.parkingSlots.ParkingSlot;
import com.kurylchyk.model.vehicles.Vehicle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/parkingTicketCreation")
public class ParkingTicketCreation extends HttpServlet {
    private ParkingTicketDAO parkingTicketDAO;

    @Override
    public void init() throws ServletException {
        parkingTicketDAO = new ParkingTicketDAO();

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("IN PARKING TICKET CREATION");

        HttpSession session = req.getSession();

        Vehicle vehicle = (Vehicle) session.getAttribute("vehicle");
        System.out.println(vehicle);
        Customer customer = (Customer) session.getAttribute("customer");
        System.out.println(customer);
        ParkingSlot parkingSlot = (ParkingSlot) session.getAttribute("parkingSlot");
        System.out.println(parkingSlot);
        connectCustomerToVehicle(vehicle,customer);


        ParkingTicket parkingTicket = new ParkingTicket(vehicle,parkingSlot,customer);
        Integer id =  parkingTicketDAO.insert(parkingTicket);
        System.out.println(id);
        parkingTicket.setParkingTicketID(id);

        session.setAttribute("parkingTicket", parkingTicket);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("parkingTicketInfo.jsp");
        requestDispatcher.forward(req,resp);


    }

    private void connectCustomerToVehicle(Vehicle vehicle,Customer customer) {

        VehicleDao vehicleDao =  new VehicleDao();
        vehicleDao.updateCustomerID(vehicle,customer);

    }

    public static void main(String[] args) {

    }
}
