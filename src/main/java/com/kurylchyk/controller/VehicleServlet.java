package com.kurylchyk.controller;


import com.kurylchyk.model.parkingTicket.Status;
import com.kurylchyk.model.services.VehicleService;
import com.kurylchyk.model.services.impl.BusinessServiceFactory;
import com.kurylchyk.model.services.impl.utilVehicle.*;
import com.kurylchyk.model.vehicles.CarSize;
import com.kurylchyk.model.vehicles.VehicleType;
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

    protected void doGetVehicle(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String licensePlate = req.getParameter("vehicleID");
        try {
            Vehicle vehicle = vehicleService.find(licensePlate);
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
        Vehicle vehicle = null;
        RequestDispatcher requestDispatcher = null;

        String typeOfVehicle = req.getParameter("typeOfVehicle");
        String make = req.getParameter("make");
        String model = req.getParameter("model");
        String licencePlate = req.getParameter("licensePlate");

        try {
            switch (VehicleType.valueOf(typeOfVehicle)) {
                case MOTORBIKE:
                    vehicle = vehicleService.create(new MotorbikeInfo(make, model, licencePlate));
                    break;
                case CAR:
                    vehicle = vehicleService.create(new CarInfo(make, model, licencePlate, CarSize.valueOf(req.getParameter("carSize"))));
                    break;
                case TRUCK:
                    vehicle = vehicleService.create(new TruckInfo(make, model, licencePlate, Boolean.parseBoolean(req.getParameter("trailerPresent"))));
                    break;
                case BUS:
                    vehicle = vehicleService.create(new BusInfo(make, model, licencePlate, Integer.parseInt(req.getParameter("countOfSeats"))));
            }
        } catch (Exception exception) {
            System.out.println("Exception  is vehicle create");
            exception.printStackTrace();
            req.setAttribute("exception", exception);
            requestDispatcher = req.getRequestDispatcher("/errorPage.jsp");
        }

        System.out.println(vehicle);
        if (requestDispatcher == null) {

            req.getSession().setAttribute("vehicle", vehicle);
            requestDispatcher = req.getRequestDispatcher(req.getContextPath() + "/parkingSlot/showAvailable");
        }

        requestDispatcher.forward(req, resp);
    }

    protected void doEdit(HttpServletRequest req, HttpServletResponse resp) {
        String vehicleID = req.getParameter("vehicleID");
        VehicleType vehicleType = VehicleType.valueOf(req.getParameter("vehicleType"));

        System.out.println("In vehicle/edit "+vehicleID + " "+vehicleType);
        try {
            Vehicle currentVehicle = vehicleService.getFromDB(vehicleID, vehicleType);
            System.out.println("currentVehicle " + currentVehicle);
            req.getSession().setAttribute("vehicle", currentVehicle);
            System.out.println("vehicle attribute was set");
            req.getRequestDispatcher("/vehicleRegistration.jsp").forward(req, resp);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }


    protected void doUpdate(HttpServletRequest req, HttpServletResponse resp) {

        System.out.println("In vehicle updation");

        Vehicle currentVehicle = (Vehicle) req.getSession().getAttribute("vehicle");
        req.getSession().removeAttribute("vehicle");
        System.out.println("Current vehicle " + currentVehicle);

        String make = req.getParameter("make");
        String model = req.getParameter("model");
        String licensePlate = req.getParameter("licensePlate");
        VehicleType vehicleType = VehicleType.valueOf(req.getParameter("typeOfVehicle"));
        VehicleInfo vehicleInfo = null;

        try {
            switch (vehicleType) {
                case MOTORBIKE:
                    vehicleInfo = new MotorbikeInfo(make, model, licensePlate);
                    break;
                case CAR:
                    vehicleInfo = new CarInfo(make, model, licensePlate, CarSize.valueOf(req.getParameter("carSize")));
                    break;
                case TRUCK:
                    vehicleInfo = new TruckInfo(make, model, licensePlate, Boolean.parseBoolean(req.getParameter("trailerPresent")));
                    break;
                case BUS:
                    vehicleInfo = new BusInfo(make, model, licensePlate, Integer.parseInt(req.getParameter("countOfSeats")));
            }
            Vehicle updatedVehicle = vehicleService.update(vehicleInfo, currentVehicle.getLicensePlate());

            req.getRequestDispatcher("/parkingTicket/showByVehicle?vehicleID="+updatedVehicle.getLicensePlate()).forward(req, resp);

        } catch (Exception exception) {
            exception.printStackTrace();
        }


    }

    protected void showAll(HttpServletRequest req, HttpServletResponse resp) {

        String vehicleStatus = req.getParameter("status");
        VehicleType vehicleType = VehicleType.valueOf(req.getParameter("vehicleType"));


        System.out.println("in do show "+vehicleType);
        List<Vehicle> allVehicles;
        try {
            if (vehicleStatus.equalsIgnoreCase("ALL")) {
                 allVehicles = vehicleService.getAll(vehicleType);
            } else {
                Status status = Status.valueOf(vehicleStatus);
                 allVehicles = vehicleService.getAll(status,vehicleType);
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


}

