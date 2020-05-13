package com.kurylchyk.controller;

import com.kurylchyk.model.Customer;
import com.kurylchyk.model.ParkingTicket;
import com.kurylchyk.model.dao.CustomerDao;
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

    {

    }

    @Override
    public void init() throws ServletException {
        parkingTicketDAO = new ParkingTicketDAO();

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {



        HttpSession session = req.getSession();

        Vehicle vehicle = (Vehicle) session.getAttribute("vehicle");
        Customer customer = (Customer) session.getAttribute("customer");
        ParkingSlot parkingSlot = (ParkingSlot) session.getAttribute("parkingSlot");

        session.removeAttribute("vehicle");
        session.removeAttribute("customer");
        session.removeAttribute("parkingSlot");


        connectCustomerToVehicle(vehicle,customer);
        ParkingTicket parkingTicket = new ParkingTicket(vehicle,parkingSlot,customer);
        Integer id =  parkingTicketDAO.insert(parkingTicket);
        parkingTicket.setParkingTicketID(id);


        session.setAttribute("currentTicket", parkingTicket);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("parkingTicketInfo.jsp");
        requestDispatcher.forward(req,resp);


    }




    private void connectCustomerToVehicle(Vehicle vehicle,Customer customer) {

        VehicleDao vehicleDao =  new VehicleDao();
        vehicleDao.updateCustomerID(vehicle,customer);

    }
}
