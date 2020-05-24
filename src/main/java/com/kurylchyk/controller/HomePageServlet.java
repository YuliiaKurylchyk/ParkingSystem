package com.kurylchyk.controller;

import com.kurylchyk.model.ParkingLot;
import com.kurylchyk.model.dao.ParkingTicketDAO;
import com.kurylchyk.model.dao.VehicleDAO;
import com.kurylchyk.model.exceptions.NoSuchParkingTicketException;
import com.kurylchyk.model.parkingSlots.SizeOfSlot;
import com.kurylchyk.model.vehicles.TypeOfVehicle;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/")
public class HomePageServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        clearSession(req.getSession());
        String action = req.getServletPath();
        switch (action) {
            case "/new":
                showNewForm(req, resp);
                break;
            case "/update":
                updateEntity(req, resp);
                break;
            case "/remove":
                removeEntity(req, resp);
                break;
            case "/edit":
                showEditForm(req, resp);
                break;
            case "/show":
                showParkingTickets(req, resp);
                break;
            default:
                showHomePage(req, resp);
                break;
        }


    }

    private void showNewForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("creationServlet?action=");
        dispatcher.forward(req, resp);
    }

    private void showHomePage(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        VehicleDAO vehicleDao = new VehicleDAO();
        ParkingTicketDAO parkingTicketDAO  = new ParkingTicketDAO();
        Integer numberOfBikes = parkingTicketDAO.countAllPresent(TypeOfVehicle.MOTORBIKE);
        Integer numberOfCars = parkingTicketDAO.countAllPresent(TypeOfVehicle.CAR);
        Integer numberOfTrucks = parkingTicketDAO.countAllPresent(TypeOfVehicle.TRUCK);
        Integer numberOfBuses = parkingTicketDAO.countAllPresent(TypeOfVehicle.BUS);
        Integer countOfTodayEntities = parkingTicketDAO.selectInDate(LocalDateTime.now()).size();
        Integer countOfAllLeft = parkingTicketDAO.selectAll("left").size();
        Integer countOfAllPresent = parkingTicketDAO.selectAll("present").size();

        req.setAttribute("numberOfBikes", numberOfBikes);
        req.setAttribute("numberOfCars", numberOfCars);
        req.setAttribute("numberOfTrucks", numberOfTrucks);
        req.setAttribute("numberOfBuses", numberOfBuses);
        req.setAttribute("countOfTodayEntities",countOfTodayEntities);
        req.setAttribute("countOfAllPresent",countOfAllPresent);
        req.setAttribute("countOfAllLeft",countOfAllLeft);

        req.setAttribute("smallSlots", ParkingLot.getCountOfSlot(SizeOfSlot.SMALL));
        req.setAttribute("mediumSlots", ParkingLot.getCountOfSlot(SizeOfSlot.MEDIUM));
        req.setAttribute("largeSlots", ParkingLot.getCountOfSlot(SizeOfSlot.LARGE));
        RequestDispatcher dispatcher = req.getRequestDispatcher("home.jsp");
        dispatcher.forward(req, resp);
    }

    private void showParkingTickets(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("showAllTickets.jsp");
        requestDispatcher.forward(req, resp);

    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        Integer id = Integer.parseInt(request.getParameter("id"));
        ParkingTicketDAO parkingTicketDAO = new ParkingTicketDAO();

        HttpSession session = request.getSession();
        try {
            session.setAttribute("currentTicket", parkingTicketDAO.select(id));
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("parkingTicketInfo.jsp");
            requestDispatcher.forward(request, response);
        }catch (NoSuchParkingTicketException exception) {
            //do something
        }
    }

    private void updateEntity(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.getSession().setAttribute("action","update");
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("searchPage.jsp");
        requestDispatcher.forward(req, resp);
    }

    private void removeEntity(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException {

        req.getSession().setAttribute("action","deleting");
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("searchPage.jsp");
        requestDispatcher.forward(req,resp);
    }

    private void clearSession(HttpSession session){
        if(session.getAttribute("currentTicket")!=null){
            session.removeAttribute("currentTicket");
        }

        if(session.getAttribute("vehicle")!=null){
            session.removeAttribute("vehicle");
        }

        if(session.getAttribute("customer")!=null){
           session.removeAttribute("customer");
        }
        if(session.getAttribute("appropriateTickets")!=null){
            session.removeAttribute("appropriateTickets");
        }


    }

}
