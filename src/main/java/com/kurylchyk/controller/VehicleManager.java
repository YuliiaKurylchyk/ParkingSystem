package com.kurylchyk.controller;

import com.kurylchyk.model.dao.VehicleDAO;
import com.kurylchyk.model.exceptions.NoSuchVehicleFoundException;
import com.kurylchyk.model.vehicles.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class VehicleManager {
    private static VehicleDAO vehicleDAO = new VehicleDAO();

    static  void validateVehicle(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

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

    static boolean checkIfVehicleIsInDB(String licence_plate) {
        boolean presentInDB = false;
        try{
            Vehicle currentVehicle = vehicleDAO.select(licence_plate);
            presentInDB  = currentVehicle!=null;
        }catch (NoSuchVehicleFoundException ex) {
            ex.printStackTrace();
        }
        return presentInDB;
    }

    static boolean checkIfPresent(String licence_plate) {

        return vehicleDAO.checkIfPresent(licence_plate);

    }

    static Vehicle createVehicle(String make, String model, String licencePlate, TypeOfVehicle type) {

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

    static Vehicle getVehicleFromDB(String licencePlate){
        Vehicle vehicle = null;
        try{
            vehicle = vehicleDAO.select(licencePlate);
        }catch (NoSuchVehicleFoundException ex){
            ex.printStackTrace();
        }
        return vehicle;
    }

}
