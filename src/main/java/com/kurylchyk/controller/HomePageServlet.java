package com.kurylchyk.controller;

import com.kurylchyk.model.domain.parkingSlots.slotEnum.SlotSize;
import com.kurylchyk.model.domain.parkingTicket.ticketEnum.Status;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.services.ParkingTicketService;
import com.kurylchyk.model.services.VehicleService;
import com.kurylchyk.model.services.impl.ParkingSlotServiceImpl;
import com.kurylchyk.model.services.impl.ServiceFacade;
import com.kurylchyk.model.domain.vehicles.vehicleEnum.VehicleType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;


@WebServlet("/home/*")
public class HomePageServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(HomePageServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       try {
           showHomePage(req, resp);
       }catch (Exception exception){
           logger.error(exception);
       }
    }


    private void showHomePage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ParkingSystemException {

        //   clearSession(req.getSession());

        try {
            setLanguage(req);
            getVehicleInfo(req);
            getParkingTicketInfo(req);

        } catch(Exception exception){
            exception.printStackTrace();
        }

        req.setAttribute("smallSlots", new ParkingSlotServiceImpl().countAvailableSlot(SlotSize.SMALL));
        req.setAttribute("mediumSlots", new ParkingSlotServiceImpl().countAvailableSlot(SlotSize.MEDIUM));
        req.setAttribute("largeSlots", new ParkingSlotServiceImpl().countAvailableSlot(SlotSize.LARGE));



        req.getRequestDispatcher("/home.jsp").forward(req,resp);
    }


    protected void getVehicleInfo(HttpServletRequest req) throws Exception {

        VehicleService vehicleService = new ServiceFacade().forVehicle();

        Integer numberOfBikes = vehicleService.countAllPresent(VehicleType.MOTORBIKE);
        Integer numberOfCars = vehicleService.countAllPresent(VehicleType.CAR);
        Integer numberOfTrucks = vehicleService.countAllPresent(VehicleType.TRUCK);
        Integer numberOfBuses = vehicleService.countAllPresent(VehicleType.BUS);

        req.setAttribute("numberOfBikes", numberOfBikes);
        req.setAttribute("numberOfCars", numberOfCars);
        req.setAttribute("numberOfTrucks", numberOfTrucks);
        req.setAttribute("numberOfBuses", numberOfBuses);


    }

    protected void getParkingTicketInfo(HttpServletRequest req) throws Exception {

        ParkingTicketService  parkingTicketService = new ServiceFacade().forParkingTicket();

        Integer countOfTodayEntities = parkingTicketService.getAllInDate(LocalDateTime.now()).size();
        Integer countOfAllLeft = parkingTicketService.getAll(Status.LEFT).size();
        Integer countOfAllPresent = parkingTicketService.getAll(Status.PRESENT).size();
        req.setAttribute("countOfTodayEntities", countOfTodayEntities);
        req.setAttribute("countOfAllPresent", countOfAllPresent);
        req.setAttribute("countOfAllLeft", countOfAllLeft);
    }

    protected void setLanguage(HttpServletRequest req){

        HttpSession session = req.getSession();
        if (session.isNew()) {
            session.setAttribute("sessionLocale", "uk");
        } else {
            if (req.getParameter("language") != null) {
                session.setAttribute("sessionLocale", req.getParameter("language"));
            }
        }
    }

/*

    private void clearSession(HttpSession session) {

        System.out.println("in clear session!!!!!");
        if (session.getAttribute("currentTicket") != null) {
            session.removeAttribute("currentTicket");
        }
        if(session.getAttribute("action")!=null){
            session.removeAttribute("action");
        }

        if (session.getAttribute("vehicle") != null) {
            session.removeAttribute("vehicle");
        }

        if (session.getAttribute("customer") != null) {
            session.removeAttribute("customer");
        }
        if (session.getAttribute("appropriateTickets") != null) {
            session.removeAttribute("appropriateTickets");
        }
    }

 */

}
