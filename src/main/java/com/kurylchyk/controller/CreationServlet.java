package com.kurylchyk.controller;

import com.kurylchyk.model.Customer;
import com.kurylchyk.model.ParkingTicket;
import com.kurylchyk.model.dao.CustomerDAO;
import com.kurylchyk.model.dao.ParkingTicketDAO;
import com.kurylchyk.model.dao.VehicleDAO;
import com.kurylchyk.model.exceptions.NoSuchCustomerFoundException;
import com.kurylchyk.model.exceptions.SuchVehiclePresentException;
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
    private boolean vehicleIsInDB;
    private boolean customerIsInDB;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


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

        VehicleManager.validateVehicle(req, resp);
        TypeOfVehicle typeOfVehicle = TypeOfVehicle.valueOf(req.getParameter("type_of_vehicle"));
        String make = req.getParameter("make");
        String model = req.getParameter("model");
        String licencePlate = req.getParameter("licence_plate");

        if (VehicleManager.checkIfVehicleIsInDB(licencePlate)) {
            if (VehicleManager.checkIfPresent(licencePlate)) {
                req.setAttribute("exception", new SuchVehiclePresentException("Such vehicle with licence plate " + licencePlate + " is already present"));
                getServletConfig().getServletContext().getRequestDispatcher("/errorPage.jsp").forward(req, resp);
            } else {
                vehicleIsInDB = true;
                createdVehicle = VehicleManager.getVehicleFromDB(licencePlate);
            }
        } else {
            createdVehicle = VehicleManager.createVehicle(make, model, licencePlate, typeOfVehicle);
        }
        ParkingSlot parkingSlot = null;
        parkingSlot = ParkingTicketManager.getParkingSlot(req, resp, createdVehicle);
        createdSlot = parkingSlot;

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("customerRegistration.jsp");
        requestDispatcher.forward(req, resp);

    }

    private void createCustomer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        CustomerManager.validateCustomer(req, resp);
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String phoneNumber = req.getParameter("phone_number");

        Integer id = CustomerManager.checkCustomerIsInDB(phoneNumber);
        if (id != null) {
            customerIsInDB = true;
            createdCustomer = CustomerManager.getCustomerFromDB(id);
        } else {
            createdCustomer = CustomerManager.createCustomer(name, surname, phoneNumber);
        }
        createParkingTicket(req, resp);

    }

    private void createParkingTicket(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (!vehicleIsInDB) {
            addVehicleToDB(createdVehicle);
        }
        if (!customerIsInDB) {
            addCustomerToDB(createdCustomer);
        }
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

    private Integer addCustomerToDB(Customer customer) {
        return customerDao.insert(customer);
    }

    private void addVehicleToDB(Vehicle vehicle) {
        vehicleDao.insert(vehicle);
    }


}
