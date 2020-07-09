package com.kurylchyk.controller;


import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.parkingTicket.Status;
import com.kurylchyk.model.services.VehicleService;
import com.kurylchyk.model.services.impl.ServiceFacade;
import com.kurylchyk.model.services.impl.utilVehicle.*;
import com.kurylchyk.model.vehicles.CarSize;
import com.kurylchyk.model.vehicles.VehicleType;
import com.kurylchyk.model.vehicles.Vehicle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

    private VehicleService vehicleService = new ServiceFacade().forVehicle();
    private static Logger logger = LogManager.getLogger(VehicleServlet.class);

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {

        String command = req.getPathInfo();

       try {
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
       }catch (Exception exception){
           logger.error(exception);
       }
    }

    protected void doGetVehicle(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String licensePlate = req.getParameter("vehicleID");
        try {
            Vehicle vehicle = vehicleService.find(licensePlate);
            req.setAttribute("vehicle", vehicle);
            req.getRequestDispatcher("/parkingTicket/showByVehicle?").forward(req, resp);
        } catch (ParkingSystemException exception) {
            logger.error(exception);
            req.setAttribute("NotFound", exception);
            req.getRequestDispatcher("/searchPage.jsp").forward(req, resp);
        }


    }

    protected void doCreate(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Vehicle vehicle = null;
        VehicleInfo vehicleInfo = null;
        try {
            vehicleInfo = VehicleInfoFactory.getVehicleInformation(req);
            vehicle = vehicleService.create(vehicleInfo);
        } catch (Exception exception) {
            logger.error(exception);
            req.setAttribute("exception", exception);
            req.getRequestDispatcher("/errorPage.jsp").forward(req, resp);
            return;
        }

        System.out.println(vehicle);
        req.getSession().setAttribute("vehicle", vehicle);
        resp.sendRedirect("/parkingSlot/showAvailable");
    }

    protected void doEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String vehicleID = req.getParameter("vehicleID");
        VehicleType vehicleType = VehicleType.valueOf(req.getParameter("vehicleType"));

        try {
            Vehicle currentVehicle = vehicleService.getFromDB(vehicleID, vehicleType);
            req.getSession().setAttribute("vehicle", currentVehicle);
            req.getRequestDispatcher("/vehicleRegistration.jsp").forward(req, resp);
        } catch (ParkingSystemException exception) {
            logger.error(exception);
        }

    }


    protected void doUpdate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Vehicle currentVehicle = (Vehicle) req.getSession().getAttribute("vehicle");
        req.getSession().removeAttribute("vehicle");
        VehicleInfo vehicleInfo;
        try {
            vehicleInfo = VehicleInfoFactory.getVehicleInformation(req);
            Vehicle updatedVehicle = vehicleService.update(vehicleInfo, currentVehicle.getLicensePlate());
            req.getRequestDispatcher("/parkingTicket/showByVehicle?vehicleID=" + updatedVehicle.getLicensePlate()).forward(req, resp);

        } catch (ParkingSystemException exception) {
            logger.error(exception);
        }


    }

    protected void showAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String vehicleStatus = req.getParameter("status");
        VehicleType vehicleType = VehicleType.valueOf(req.getParameter("vehicleType"));
        List<Vehicle> allVehicles;
        try {
            if (vehicleStatus.equalsIgnoreCase("ALL")) {
                allVehicles = vehicleService.getAll(vehicleType);
            } else {
                Status status = Status.valueOf(vehicleStatus);
                allVehicles = vehicleService.getAll(status, vehicleType);
            }
            req.setAttribute("allVehicles", allVehicles);
            req.getRequestDispatcher("/showAllVehicle.jsp").forward(req, resp);
        } catch (ParkingSystemException exception) {
            logger.error(exception);
        }


    }

    protected void showRegistrationFrom(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.getRequestDispatcher("/vehicleRegistration.jsp").forward(req, resp);
    }


}

