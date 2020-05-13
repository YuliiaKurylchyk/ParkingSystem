package com.kurylchyk.controller;


import com.kurylchyk.model.ParkingTicket;
import com.kurylchyk.model.dao.ParkingTicketDAO;
import com.kurylchyk.model.dao.VehicleDao;
import com.kurylchyk.model.vehicles.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/vehicleUpdating")
public class VehicleUpdating extends HttpServlet {
    private VehicleDao vehicleDao = new VehicleDao();


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        HttpSession session = req.getSession();
        Vehicle currentVehicle = (Vehicle) session.getAttribute("vehicle");
        System.out.println(currentVehicle);
        ParkingTicket currentTicket = (ParkingTicket) session.getAttribute("currentTicket");
        if( currentTicket==null ){
            currentTicket = ParkingTicketManager.getTicketByVehicle(currentVehicle);

        }
        System.out.println(currentVehicle);
        System.out.println(currentTicket);

        String make = req.getParameter("make");
        String model = req.getParameter("model");
        String licencePlate = req.getParameter("licence_plate");
        String type = req.getParameter("type_of_vehicle");

        Vehicle updatedVehicle = getCreatedVehicle(make, model, licencePlate, TypeOfVehicle.valueOf(type));
        System.out.println(updatedVehicle);
        if (currentVehicle.getLicencePlate().equals(updatedVehicle.getLicencePlate())) {

            new ParkingTicketDAO().updateVehicleID(currentTicket.getParkingTicketID(), updatedVehicle.getLicencePlate());
        }

        vehicleDao.update(updatedVehicle, currentVehicle.getLicencePlate());
        currentTicket.setVehicle(updatedVehicle);
        session.setAttribute("currentTicket",currentTicket);
        session.removeAttribute("vehicle");
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("parkingTicketInfo.jsp");
        requestDispatcher.forward(req,resp);

        //... check licence plate and type
        //--- do something with parking slot(return it back and get new);
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
}
