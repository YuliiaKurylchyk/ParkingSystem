package com.kurylchyk.controller;

import com.kurylchyk.model.customer.Customer;
import com.kurylchyk.model.parkingTicket.ParkingTicket;
import com.kurylchyk.model.dao.CustomerDAO;
import com.kurylchyk.model.dao.ParkingTicketDAO;
import com.kurylchyk.model.dao.VehicleDAO;
import com.kurylchyk.model.exceptions.NoSuchCustomerFoundException;
import com.kurylchyk.model.exceptions.NoSuchVehicleFoundException;
import com.kurylchyk.model.parkingSlots.ParkingSlot;
import com.kurylchyk.model.vehicles.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/updatingServlet")
public class UpdatingServlet extends HttpServlet {

    private CustomerDAO customerDao = new CustomerDAO();
    private VehicleDAO vehicleDao = new VehicleDAO();
    private ParkingTicketDAO parkingTicketDAO = new ParkingTicketDAO();
    private RequestDispatcher requestDispatcher;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String option = req.getParameter("option");

        switch (option) {
            case "vehicle":
                changeVehicle(req, resp);
                break;
            case "customer":
                changeCustomer(req, resp);
                break;
            case "updateVehicle":
                updateVehicle(req, resp);
                break;
            case "updateCustomer":
                updateCustomer(req, resp);
                break;
        }

    }

    private void retrieveVehicleFromDB(HttpServletRequest req, HttpServletResponse resp) throws NoSuchVehicleFoundException {
        vehicleDao = new VehicleDAO();
        Vehicle vehicle = vehicleDao.select(req.getParameter("vehicleID"));
        HttpSession session = req.getSession();
        session.setAttribute("vehicle", vehicle);
    }

    private void changeVehicle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            retrieveVehicleFromDB(req, resp);
            requestDispatcher = req.getRequestDispatcher("vehicleRegistration.jsp");
            requestDispatcher.forward(req, resp);
        } catch (NoSuchVehicleFoundException ex) {
            // forward to page
        }

    }

    private void updateVehicle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Vehicle currentVehicle = (Vehicle) session.getAttribute("vehicle");

        ParkingTicket currentTicket = (ParkingTicket) session.getAttribute("currentTicket");
        if (currentTicket == null) {
            currentTicket = ParkingTicketManager.getTicketByVehicle(currentVehicle);
        }
        Vehicle updatedVehicle = getUpdatedVehicle(req, resp);

        System.out.println(updatedVehicle);
        if (currentVehicle.getLicencePlate().equals(updatedVehicle.getLicencePlate())) {
            new ParkingTicketDAO().updateVehicleID(currentTicket.getParkingTicketID(), updatedVehicle.getLicencePlate());
        }
        if (currentVehicle.getTypeOfVehicle() != updatedVehicle.getTypeOfVehicle()) {
            ParkingTicketManager.setParkingSlotBack(currentTicket.getParkingSlot());
            ParkingSlot newParkingSlot = ParkingTicketManager.getParkingSlot(req, resp, updatedVehicle);
            currentTicket.setParkingSlot(newParkingSlot);
        }

        vehicleDao.update(updatedVehicle, currentVehicle.getLicencePlate());
        currentTicket.setVehicle(updatedVehicle);
        session.setAttribute("currentTicket", currentTicket);
        session.removeAttribute("vehicle");
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("parkingTicketInfo.jsp");
        requestDispatcher.forward(req, resp);
    }

    private Vehicle getUpdatedVehicle(HttpServletRequest req, HttpServletResponse resp) {

        String make = req.getParameter("make");
        String model = req.getParameter("model");
        String licencePlate = req.getParameter("licence_plate");
        TypeOfVehicle type = TypeOfVehicle.valueOf(req.getParameter("typeOfVehicle"));

        Vehicle vehicle = Vehicle.newVehicle()
                .setType(type)
                .setMake(make)
                .setModel(model)
                .setLicencePlate(licencePlate)
                .buildVehicle();
        return vehicle;
    }

    private void retrieveCustomerFromDB(HttpServletRequest req, HttpServletResponse resp) {

        customerDao = new CustomerDAO();
        try {
            Customer customer = customerDao.select(Integer.parseInt(req.getParameter("customerID")));
            System.out.println(customer);

            HttpSession session = req.getSession();
            session.setAttribute("customer", customer);

        } catch (NoSuchCustomerFoundException exception) {
            //do something
        }
    }

    private void changeCustomer(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        retrieveCustomerFromDB(req, resp);
        requestDispatcher = req.getRequestDispatcher("customerRegistration.jsp");
        requestDispatcher.forward(req, resp);
    }

    private void updateCustomer(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        Customer customer = getUpdatedCustomer(req, resp);
        customerDao.update(customer, customer.getCustomerID());

        ParkingTicket currentTicket = (ParkingTicket) session.getAttribute("currentTicket");
        if (currentTicket == null) {
            List<ParkingTicket> allFoundTickets = parkingTicketDAO.selectByCustomerID(customer.getCustomerID());
            req.setAttribute("appropriateTickets", allFoundTickets);
            req.setAttribute("onlyCurrentCustomer","");
            requestDispatcher = req.getRequestDispatcher("showAllTickets.jsp");

        }else{
            currentTicket.setCustomer(customer);
            session.setAttribute("currentTicket", currentTicket);
            requestDispatcher = req.getRequestDispatcher("parkingTicketInfo.jsp");
        }

        session.removeAttribute("customer");
        requestDispatcher.forward(req, resp);
    }

    private Customer getUpdatedCustomer(HttpServletRequest req, HttpServletResponse resp) {

        HttpSession session = req.getSession();

        Integer currentID = ((Customer) session.getAttribute("customer")).getCustomerID();
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String phoneNumber = req.getParameter("phone_number");
        Customer customer = new Customer(currentID, name, surname, phoneNumber);
        System.out.println(customer);
        return customer;
}

}
