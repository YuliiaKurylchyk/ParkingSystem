package com.kurylchyk.controller;

import com.kurylchyk.controller.validation.CustomerValidation;
import com.kurylchyk.controller.validation.VehicleValidation;
import com.kurylchyk.model.customer.Customer;
import com.kurylchyk.model.exceptions.NoSuchCustomerFoundException;
import com.kurylchyk.model.exceptions.NoSuchVehicleFoundException;
import com.kurylchyk.model.parkingTicket.ParkingTicket;
import com.kurylchyk.model.dao.CustomerDAO;
import com.kurylchyk.model.dao.ParkingTicketDAO;
import com.kurylchyk.model.dao.VehicleDAO;
import com.kurylchyk.model.exceptions.SuchVehiclePresentException;
import com.kurylchyk.model.parkingSlots.ParkingSlot;
import com.kurylchyk.model.service.CustomerService;
import com.kurylchyk.model.service.ParkingTicketService;
import com.kurylchyk.model.service.VehicleService;
import com.kurylchyk.model.vehicles.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/creationServlet")
public class CreationServlet extends HttpServlet {
    private VehicleService vehicleService = new VehicleService();
    private CustomerService customerService = new CustomerService();
    private ParkingTicketService parkingTicketService = new ParkingTicketService();

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


        VehicleValidation vehicleValidation = VehicleValidation.fromRequestParameters(req);
        List<String> violations = vehicleValidation.validate();
        if (!violations.isEmpty()) {
            vehicleValidation.setAsRequestAttribute(req);
            req.setAttribute("violations", violations);
            req.getRequestDispatcher("vehicleRegistration.jsp").forward(req, resp);

        }

        TypeOfVehicle typeOfVehicle = TypeOfVehicle.valueOf(req.getParameter("typeOfVehicle"));
        String make = req.getParameter("make");
        String model = req.getParameter("model");
        String licencePlate = req.getParameter("licencePlate");

        if (vehicleService.isPresent(licencePlate)) {
            if (vehicleService.getStatus(licencePlate).equals("present")) {
                req.setAttribute("exception", new SuchVehiclePresentException("Such vehicle with licence plate " + licencePlate + " is already present"));
                getServletConfig().getServletContext().getRequestDispatcher("/errorPage.jsp").forward(req, resp);

            } else {
                vehicleIsInDB = true;
                try {
                    createdVehicle = vehicleService.get(licencePlate);
                } catch (NoSuchVehicleFoundException exception) {
                    exception.printStackTrace();
                }
            }
        } else {
            createdVehicle = VehicleManager.createVehicle(make, model, licencePlate, typeOfVehicle);
        }

        createdSlot = ParkingTicketManager.getParkingSlot(req, resp, createdVehicle);

        System.out.println(createdSlot);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("customerRegistration.jsp");
        requestDispatcher.forward(req, resp);

    }

    private void createCustomer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        CustomerValidation customerValidation = CustomerValidation.fromRequestParameters(req);
        List<String> violations = customerValidation.validate();
        //customerValidation.setAsRequestAttribute(req);

        if (!violations.isEmpty()) {
            customerValidation.setAsRequestAttribute(req);
            req.setAttribute("violations", violations);
            req.getRequestDispatcher("customerRegistration.jsp").forward(req, resp);
        }

        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String phoneNumber = req.getParameter("phoneNumber");
        Customer customer;
        if (customerService.isPresent(phoneNumber)) {
            try {
                customer = customerService.get(phoneNumber);
                customerIsInDB = true;
            } catch (NoSuchCustomerFoundException exception) {
                exception.printStackTrace();
            }
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

        System.out.println(createdSlot);
        ParkingTicket parkingTicket = ParkingTicket.newParkingTicket()
                .withVehicle(createdVehicle)
                .withCustomer(createdCustomer)
                .withParkingSlot(createdSlot)
                .withArrivalTime(LocalDateTime.now())
                .withStatus("present")
                .buildTicket();
        Integer id = parkingTicketService.add(parkingTicket);
        parkingTicket.setParkingTicketID(id);

        session.setAttribute("currentTicket", parkingTicket);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("parkingTicketInfo.jsp");
        requestDispatcher.forward(req, resp);
    }

    private void connectCustomerToVehicle(Vehicle vehicle, Customer customer) {


        vehicleService.updateCustomerID(vehicle.getLicencePlate(), customer.getCustomerID());

    }

    private void addCustomerToDB(Customer customer) {
        Integer id = customerService.add(customer);
        customer.setCustomerID(id);
    }

    private void addVehicleToDB(Vehicle vehicle) {
        vehicleService.add(vehicle);
    }


}
