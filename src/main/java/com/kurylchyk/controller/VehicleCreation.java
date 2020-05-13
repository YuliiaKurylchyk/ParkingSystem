package com.kurylchyk.controller;


import com.kurylchyk.model.ParkingLot;
import com.kurylchyk.model.dao.VehicleDao;
import com.kurylchyk.model.exceptions.NoAvailableParkingSlotException;
import com.kurylchyk.model.exceptions.NoSuchTypeOfVehicleException;
import com.kurylchyk.model.parkingSlots.ParkingSlot;
import com.kurylchyk.model.vehicles.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@WebServlet("/vehicleCreation")
public class VehicleCreation extends HttpServlet {
    private VehicleDao vehicleDao;

    @Override
    public void init() throws ServletException {
        vehicleDao = new VehicleDao();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        TypeOfVehicle typeOfVehicle = TypeOfVehicle.valueOf(req.getParameter("type_of_vehicle"));
        String make = req.getParameter("make");
        String model = req.getParameter("model");
        String licencePlate = req.getParameter("licence_plate");


        System.out.println(licencePlate);
        System.out.println("Vehicle creation");
        if(!validateLicencePlate(licencePlate)) {

        }else {
            Vehicle vehicle = getCreatedVehicle(make, model, licencePlate, typeOfVehicle);
            ParkingSlot parkingSlot = null;
            try {
                parkingSlot = getParkingSlot(vehicle);
            } catch (NoAvailableParkingSlotException exception) {

                //throw exception!
            }
            addVehicleToDB(vehicle);
            HttpSession session = req.getSession();
            session.setAttribute("vehicle", vehicle);
            session.setAttribute("parkingSlot", parkingSlot);
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("customerRegistration.jsp");
            requestDispatcher.forward(req,resp);
        }
    }

    private ParkingSlot getParkingSlot(Vehicle vehicle) throws NoAvailableParkingSlotException {
        ParkingSlot parkingSlot = null;
        ParkingLot parkingLot = new ParkingLot();
        try {
            parkingSlot = parkingLot.getParkingSlot(vehicle);

        } catch (NoSuchTypeOfVehicleException ex) {
            //!!!!!refactor!!!!!!
            ex.printStackTrace();
        }
        return parkingSlot;
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

    private boolean validateLicencePlate(String licencePlate) {
        String regex  = "^[A-Z]{2} [0-9]{4} [A-Z]{2}$";

        return licencePlate.matches(regex);
    }

}
