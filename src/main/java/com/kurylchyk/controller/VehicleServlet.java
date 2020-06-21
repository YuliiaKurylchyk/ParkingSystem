package com.kurylchyk.controller;


import com.kurylchyk.model.exceptions.NoAvailableParkingSlotException;
import com.kurylchyk.model.exceptions.SuchVehiclePresentException;
import com.kurylchyk.model.parkingSlots.ParkingSlot;
import com.kurylchyk.model.parkingTicket.Status;
import com.kurylchyk.model.services.VehicleService;
import com.kurylchyk.model.services.impl.BusinessServiceFactory;
import com.kurylchyk.model.services.impl.ParkingLotServiceImpl;
import com.kurylchyk.model.vehicles.TypeOfVehicle;
import com.kurylchyk.model.vehicles.Vehicle;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/vehicle/*")
public class VehicleServlet extends HttpServlet {

    private VehicleService vehicleService = new BusinessServiceFactory().forVehicle();
    private Vehicle vehicle;


    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        System.out.println("In vehicle servlet");
        String command = req.getPathInfo();

        System.out.println(command);
        switch (command) {
            case "/create":
                doCreate(req, resp);
                break;
            case "/get":
                doGetVehicle(req, resp);
                break;
            case "/edit":
                doEdit(req, resp);
                break;
            case "/update":
                doUpdate(req, resp);
                break;
            case "/form":
                showRegistrationFrom(req, resp);
                break;
            case "/show":
                showAll(req, resp);
                break;

        }
    }

    protected void doGetVehicle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String licensePlate = req.getParameter("vehicleID");
        try {
            Vehicle vehicle = vehicleService.getFromDB(licensePlate);
            req.setAttribute("vehicle", vehicle);
            req.getRequestDispatcher("/parkingTicket/showByVehicle?").forward(req, resp);
        } catch (Exception exception) {
            req.setAttribute("NotFound", exception);
            System.out.println("exception in doGetVehicle");
            req.getRequestDispatcher("/searchPage.jsp").forward(req, resp);
        }

    }

    protected void doCreate(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        System.out.println("In vehicle do create!");
        RequestDispatcher requestDispatcher = null;
        Vehicle vehicle = null;

        String typeOfVehicle = req.getParameter("typeOfVehicle");
        String make = req.getParameter("make");
        String model = req.getParameter("model");
        String licencePlate = req.getParameter("licensePlate");

        try {
            List<String> violations = vehicleService.validate(make, model, licencePlate, typeOfVehicle);
            if (!violations.isEmpty()) {
                setAttributeBack(typeOfVehicle, make, model, licencePlate, req);
                req.setAttribute("violations", violations);
                requestDispatcher = req.getRequestDispatcher("/vehicleRegistration.jsp");

            }
            if (vehicleService.isPresent(licencePlate)) {
                if (vehicleService.getVehicleStatus(licencePlate).equals("present")) {
                    req.setAttribute("exception", new SuchVehiclePresentException("Such vehicle with licence plate"
                            + licencePlate + " is already present"));
                    requestDispatcher = req.getRequestDispatcher("errorPage.jsp");
                } else {
                    vehicle = vehicleService.getFromDB(licencePlate);
                }
            } else {
                vehicle = vehicleService.create(make, model, licencePlate, TypeOfVehicle.valueOf(typeOfVehicle));
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }

        if (requestDispatcher == null) {
            ParkingSlot parkingSlot = null;
            try {
                parkingSlot = getAppropriateSlot(vehicle.getTypeOfVehicle());
            } catch (NoAvailableParkingSlotException exception) {
                req.setAttribute("exception", exception);
                req.getRequestDispatcher("errorPage.jsp").forward(req, resp);
            }
            req.getSession().setAttribute("vehicle", vehicle);
            req.getSession().setAttribute("parkingSlot", parkingSlot);
            System.out.println("Forward to customer Registration");
            requestDispatcher = req.getRequestDispatcher(req.getContextPath() + "/customer/form");
        }
        requestDispatcher.forward(req, resp);
    }

    protected void doEdit(HttpServletRequest req, HttpServletResponse resp) {
        String vehicleID =req.getParameter("vehicleID");
        try{
            vehicle = vehicleService.getFromDB(vehicleID);
            System.out.println("Vehicle " + vehicle);
            req.setAttribute("vehicle",vehicle);
            System.out.println("vehicle attribute was set");
            req.getRequestDispatcher("/vehicleRegistration.jsp").forward(req,resp);
        }catch (Exception exception){
            exception.printStackTrace();
        }

    }

    protected void doUpdate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("In vehicle updation");

        Vehicle currentVehicle = vehicle;
        System.out.println("Current vehicle " + currentVehicle);

        String make = req.getParameter("make");
        String model = req.getParameter("model");
        String licensePlate = req.getParameter("licensePlate");
        TypeOfVehicle typeOfVehicle = TypeOfVehicle.valueOf(req.getParameter("typeOfVehicle"));

        Vehicle updatedVehicle = Vehicle.newVehicle().setType(typeOfVehicle)
                .setModel(model).setMake(make).setLicencePlate(licensePlate).buildVehicle();
        System.out.println("Updated vehicle " + updatedVehicle);
        try {
            vehicleService.update(updatedVehicle, currentVehicle.getLicensePlate());

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        req.setAttribute("currentVehicle", currentVehicle);
        req.setAttribute("updatedVehicle", updatedVehicle);
        req.getRequestDispatcher("/parkingTicket/updateVehicle").forward(req,resp);

    }

    protected void showAll(HttpServletRequest req, HttpServletResponse resp) {

        String vehicleStatus = req.getParameter("status");
        List<Vehicle> allVehicles;
        try {
            if (vehicleStatus.equalsIgnoreCase("ALL")) {
                allVehicles = vehicleService.getAll();
            } else {
                Status status = Status.valueOf(vehicleStatus);
                allVehicles = vehicleService.getAll(status);
            }
            req.setAttribute("allVehicles", allVehicles);
            req.getRequestDispatcher("/showAllVehicle.jsp").forward(req, resp);
        } catch (Exception exception) {
            exception.printStackTrace();
        }


    }

    protected void showRegistrationFrom(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.getRequestDispatcher("/vehicleRegistration.jsp").forward(req, resp);
    }

    private void setAttributeBack(String typeOfVehicle, String make, String model, String licensePlate, HttpServletRequest req)
    {
        req.setAttribute("typeOfVehicle", typeOfVehicle);
        req.setAttribute("make", make);
        req.setAttribute("model", model);
        req.setAttribute("licensePlate", licensePlate);
    }

    private ParkingSlot getAppropriateSlot(TypeOfVehicle typeOfVehicle) throws NoAvailableParkingSlotException {

        return new ParkingLotServiceImpl().getParkingSlot(typeOfVehicle);
    }

}

