package com.kurylchyk.controller;

import com.kurylchyk.model.dao.VehicleDao;
import com.kurylchyk.model.vehicles.TypeOfVehicle;
import com.kurylchyk.model.vehicles.Vehicle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/")
public class HomePageServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getServletPath();
        System.out.println(action);
        switch (action) {
            case "/new":
                showNewForm(req, resp);
                break;
            case "/insert":
                //insertUser(request, response);
                break;
            case "/delete":
                //deleteUser(request, response);
                break;
            case "/edit":
                //showEditForm(request, response);
                break;
            case "/show":
                showAllEntities(req, resp);
                break;
            default:
                showHomePage(req,resp);
                break;
        }


    }

    private void showNewForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("vehicleRegistration.jsp");
        dispatcher.forward(req, resp);
    }

    private void showHomePage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        VehicleDao vehicleDao = new VehicleDao();
        Integer numberOfBikes = vehicleDao.getCountOfType(TypeOfVehicle.MOTORBIKE);
        Integer numberOfCars = vehicleDao.getCountOfType(TypeOfVehicle.CAR);
        Integer numberOfTrucks = vehicleDao.getCountOfType(TypeOfVehicle.TRUCK);
        Integer numberOfBuses = vehicleDao.getCountOfType(TypeOfVehicle.BUS);

        req.setAttribute("numberOfBikes", numberOfBikes);
        req.setAttribute("numberOfCars", numberOfCars);
        req.setAttribute("numberOfTrucks", numberOfTrucks);
        req.setAttribute("numberOfBuses", numberOfBuses);

        RequestDispatcher dispatcher = req.getRequestDispatcher("home.jsp");
        dispatcher.forward(req, resp);
    }

    private void showAllEntities(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
         RequestDispatcher requestDispatcher = req.getRequestDispatcher("showAllTickets.jsp");
        requestDispatcher.forward(req, resp);


    }

}
