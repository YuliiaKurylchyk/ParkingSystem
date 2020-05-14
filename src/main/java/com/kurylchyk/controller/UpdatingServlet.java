package com.kurylchyk.controller;


import com.kurylchyk.model.Customer;
import com.kurylchyk.model.ParkingLot;
import com.kurylchyk.model.ParkingTicket;
import com.kurylchyk.model.dao.CustomerDao;
import com.kurylchyk.model.dao.ParkingTicketDAO;
import com.kurylchyk.model.dao.VehicleDao;
import com.kurylchyk.model.exceptions.NoAvailableParkingSlotException;
import com.kurylchyk.model.exceptions.NoSuchCustomerFoundException;
import com.kurylchyk.model.exceptions.NoSuchTypeOfVehicleException;
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

@WebServlet("/updatingServlet")
public class UpdatingServlet extends HttpServlet {

    private CustomerDao customerDao = new CustomerDao();
    private VehicleDao vehicleDao = new VehicleDao();
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
        vehicleDao = new VehicleDao();
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
        Vehicle vehicle = null;
        String make = req.getParameter("make");
        String model = req.getParameter("model");
        String licencePlate = req.getParameter("licence_plate");
        TypeOfVehicle type = TypeOfVehicle.valueOf(req.getParameter("type_of_vehicle"));

        switch (type) {
            case MOTORBIKE:
                vehicle = new Motorbike(make, model, licencePlate);
                break;
            case CAR:
                vehicle = new Car(make, model, licencePlate);
                break;
            case TRUCK:
                vehicle = new Truck(make, model, licencePlate);
                break;
            case BUS:
                vehicle = new Bus(make, model, licencePlate);
                break;
        }
        return vehicle;
    }


    private void retrieveCustomerFromDB(HttpServletRequest req, HttpServletResponse resp) {

        customerDao = new CustomerDao();
        try {
            Customer customer = customerDao.select(Integer.parseInt(req.getParameter("customerID")));
            System.out.println(customer);

            HttpSession session = req.getSession();
            session.setAttribute("customer", customer);

        } catch (NoSuchCustomerFoundException exception) {
            //do something
        }
    }

    private void changeCustomer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        retrieveCustomerFromDB(req, resp);
        requestDispatcher = req.getRequestDispatcher("customerRegistration.jsp");
        requestDispatcher.forward(req, resp);
    }

    private void updateCustomer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        Customer customer = getUpdatedCustomer(req, resp);
        customerDao.update(customer, customer.getCustomerID());

        ParkingTicket currentTicket = (ParkingTicket) session.getAttribute("currentTicket");
        if (currentTicket == null) {
            currentTicket = ParkingTicketManager.getTicketByCustomer(customer);
        }

        currentTicket.setCustomer(customer);
        session.setAttribute("currentTicket", currentTicket);
        session.removeAttribute("customer");
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("parkingTicketInfo.jsp");
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
