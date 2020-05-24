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

       Vehicle vehicle = Vehicle.newVehicle()
               .setType(type)
               .setMake(make)
               .setModel(model)
               .setLicencePlate(licencePlate)
               .buildVehicle();
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
