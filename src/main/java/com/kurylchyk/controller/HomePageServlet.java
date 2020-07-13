package com.kurylchyk.controller;

import com.kurylchyk.model.domain.parkingSlots.slotEnum.SlotSize;
import com.kurylchyk.model.domain.parkingTicket.ticketEnum.Status;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.services.ParkingSlotService;
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
    private VehicleService vehicleService =ServiceFacade.forVehicle();
    private ParkingTicketService parkingTicketService = ServiceFacade.forParkingTicket();
    private ParkingSlotService parkingSlotService = ServiceFacade.forParkingSlot();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
       try {
           showHomePage(req, resp);
       }catch (Exception exception){
           logger.error(exception);
       }
    }


    private void showHomePage(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException, ParkingSystemException {

        try {
            setLanguage(req);
            getVehicleInfo(req);
            getParkingTicketInfo(req);
            getParkingSlotInfo(req);

        } catch(Exception exception){
            exception.printStackTrace();
        }

        req.getRequestDispatcher("/home.jsp").forward(req,resp);
    }


    protected void getVehicleInfo(HttpServletRequest req) throws Exception {


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



        Integer countOfTodayEntities = parkingTicketService.getAllInDate(LocalDateTime.now()).size();
        Integer countOfAllLeft = parkingTicketService.getAll(Status.LEFT).size();
        Integer countOfAllPresent = parkingTicketService.getAll(Status.PRESENT).size();
        req.setAttribute("countOfTodayEntities", countOfTodayEntities);
        req.setAttribute("countOfAllPresent", countOfAllPresent);
        req.setAttribute("countOfAllLeft", countOfAllLeft);
    }

    protected  void getParkingSlotInfo(HttpServletRequest req) throws ParkingSystemException {

        req.setAttribute("smallSlots", parkingSlotService.countAvailableSlot(SlotSize.SMALL));
        req.setAttribute("mediumSlots", parkingSlotService.countAvailableSlot(SlotSize.MEDIUM));
        req.setAttribute("largeSlots", parkingSlotService.countAvailableSlot(SlotSize.LARGE));
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


}
