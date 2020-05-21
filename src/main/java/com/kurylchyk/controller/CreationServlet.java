package com.kurylchyk.controller;

import com.kurylchyk.model.Customer;
import com.kurylchyk.model.ParkingTicket;
import com.kurylchyk.model.dao.CustomerDAO;
import com.kurylchyk.model.dao.ParkingTicketDAO;
import com.kurylchyk.model.dao.VehicleDAO;
import com.kurylchyk.model.exceptions.NoSuchCustomerFoundException;
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

@WebServlet("/creationServlet")
public class CreationServlet extends HttpServlet {
    private VehicleDAO vehicleDao = new VehicleDAO();
    private CustomerDAO customerDao = new CustomerDAO();
    private ParkingTicketDAO parkingTicketDAO = new ParkingTicketDAO();
    private Vehicle createdVehicle;
    private Customer createdCustomer;
    private ParkingSlot createdSlot;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        System.out.println("IN CREATION SERVLET");

        switch (req.getParameter("action")) {
            case "vehicle":
                createVehicle(req, resp);
                break;

            case "customer":
                createCustomer(req, resp);
                break;
            default:
                req.getRequestDispatcher("vehicleRegistration.jsp").forward(req, resp);


        }
    }


    private void createVehicle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        validateVehicle(req, resp);
        TypeOfVehicle typeOfVehicle = TypeOfVehicle.valueOf(req.getParameter("type_of_vehicle"));
        String make = req.getParameter("make");
        String model = req.getParameter("model");
        String licencePlate = req.getParameter("licence_plate");

        Vehicle vehicle = getCreatedVehicle(make, model, licencePlate, typeOfVehicle);
        ParkingSlot parkingSlot = null;

        parkingSlot = ParkingTicketManager.getParkingSlot(req,resp,vehicle);

        createdVehicle = vehicle;
        createdSlot = parkingSlot;
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("customerRegistration.jsp");
        requestDispatcher.forward(req, resp);

    }

    private void createCustomer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        validateCustomer(req, resp);
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String phoneNumber = req.getParameter("phone_number");

        Integer id = checkCustomerIsInDB(phoneNumber);
        Customer customer;
        if (id == null) {
            id = addCustomerToDB(getCreatedCustomer(name, surname, phoneNumber));
        }
        try {
            customer = customerDao.select(id);
            createdCustomer = customer;
            createParkingTicket(req, resp);
        } catch (NoSuchCustomerFoundException exception) {
            //do something
        }
    }

    private void createParkingTicket(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        addVehicleToDB(createdVehicle);
        addCustomerToDB(createdCustomer);
        connectCustomerToVehicle(createdVehicle, createdCustomer);
        ParkingTicket parkingTicket = new ParkingTicket(createdVehicle, createdSlot, createdCustomer);
        Integer id = parkingTicketDAO.insert(parkingTicket);
        parkingTicket.setParkingTicketID(id);

        session.setAttribute("currentTicket", parkingTicket);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("parkingTicketInfo.jsp");
        requestDispatcher.forward(req, resp);

    }

    private void connectCustomerToVehicle(Vehicle vehicle, Customer customer) {

        VehicleDAO vehicleDao = new VehicleDAO();
        vehicleDao.updateCustomerID(vehicle, customer);

    }

    private Integer checkCustomerIsInDB(String phoneNumber) {
        try {
            return customerDao.selectIdByPhoneNumber(phoneNumber);
        } catch (NoSuchCustomerFoundException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    private Customer getCreatedCustomer(String name, String surname, String phoneNumber) {

        return new Customer(name, surname, phoneNumber);
    }

    private Integer addCustomerToDB(Customer customer) {
        return customerDao.insert(customer);
    }


    private Vehicle getCreatedVehicle(String make, String model, String licencePlate, TypeOfVehicle type) {

        Vehicle vehicle = null;
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

    private void addVehicleToDB(Vehicle vehicle) {
        vehicleDao.insert(vehicle);
    }

    private void validateVehicle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        boolean failed = false;
        RequestDispatcher requestDispatcher = null;
        String regex = "^[A-Z]{2} [0-9]{4} [A-Z]{2}$";
        if (req.getParameter("type_of_vehicle") == null) {
            failed = true;
            req.setAttribute("badType", "Choose the type of vehicle");
        }

        if (!req.getParameter("licence_plate").matches(regex)) {
            failed = true;
            req.setAttribute("badLicencePlate", "Bad format of licence plate. Try again");
        }

        if (req.getParameter("make").length() == 0 || req.getParameter("model").length() == 0) {
            failed = true;
            req.setAttribute("badData", "Enter the make and model!");
        }
        if (failed) {
            req.getRequestDispatcher("vehicleRegistration.jsp").forward(req, resp);
        }

    }

    private void validateCustomer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean failed = false;
        RequestDispatcher requestDispatcher = null;

        String regex = "^\\D*$";
        if (req.getParameter("name").equals("") || req.getParameter("surname").equals("") || !req.getParameter("name").matches(regex) || !req.getParameter("surname").matches(regex)) {

            failed = true;
            req.setAttribute("badData", "Bad format of name or surname. Try again");

        }
        String phoneNumber = req.getParameter("phone_number");
        if (phoneNumber.equals("") || !(phoneNumber.charAt(0) == '0' && phoneNumber.length() == 10 && phoneNumber.matches("[0-9]+"))) {
            failed = true;
            req.setAttribute("badPhoneNumber", "Bad format of phone number.It must start with 0. Try again");
        }

        if (failed) {
            requestDispatcher = req.getRequestDispatcher("customerRegistration.jsp");
            requestDispatcher.forward(req, resp);
        }
    }
}
