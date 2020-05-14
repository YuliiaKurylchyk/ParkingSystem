package com.kurylchyk.controller;


import com.kurylchyk.model.Customer;
import com.kurylchyk.model.ParkingTicket;
import com.kurylchyk.model.dao.CustomerDao;
import com.kurylchyk.model.dao.ParkingTicketDAO;
import com.kurylchyk.model.dao.VehicleDao;
import com.kurylchyk.model.exceptions.NoSuchCustomerFoundException;
import com.kurylchyk.model.exceptions.NoSuchParkingTicketException;
import com.kurylchyk.model.exceptions.NoSuchVehicleFoundException;
import com.kurylchyk.model.vehicles.Vehicle;
import com.sun.deploy.net.HttpResponse;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/search")
public class SearchServlet extends HttpServlet {
    private ParkingTicketDAO parkingTicketDAO;
    private CustomerDao customerDao;
    private VehicleDao vehicleDao;

    @Override
    public void init() throws ServletException {
        parkingTicketDAO = new ParkingTicketDAO();
        customerDao = new CustomerDao();
        vehicleDao = new VehicleDao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        switch (req.getParameter("option")) {
            case "parkingTicket":
                getParkingTicket(req, resp);
                break;
            case "customer":
                getCustomer(req, resp);
                break;
            case "vehicle":
                getVehicle(req, resp);
                break;
        }
    }

    // зробити обробку виключень тут!
    private void getCustomer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Customer customer = null;
        customer = ParkingTicketManager.searchCustomerByPhoneNumber(req, resp);
        req.getSession().setAttribute("customer", customer);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("customerRegistration.jsp");
        checkAction(req, resp, requestDispatcher);

    }

    private void getVehicle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Vehicle vehicle = ParkingTicketManager.searchVehicle(req, resp);
        HttpSession session = req.getSession();
        session.setAttribute("vehicle", vehicle);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("vehicleRegistration.jsp");
        checkAction(req, resp, requestDispatcher);

    }

    private void getParkingTicket(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ParkingTicket parkingTicket = ParkingTicketManager.searchParkingTicket(req, resp);
        HttpSession session = req.getSession();
        System.out.println(parkingTicket);
        session.setAttribute("currentTicket", parkingTicket);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("parkingTicketInfo.jsp");
        checkAction(req, resp, requestDispatcher);
    }

    public void checkAction(HttpServletRequest req, HttpServletResponse resp, RequestDispatcher requestDispatcher) throws ServletException, IOException {

        String action = String.valueOf(req.getSession().getAttribute("action"));
        req.getSession().removeAttribute("action");
        switch (action) {
            case "update":
                requestDispatcher.forward(req, resp);
                break;
            case "deleting":
                req.getRequestDispatcher("deletingServlet?action=remove").forward(req,resp);
                break;
        }
    }

}
