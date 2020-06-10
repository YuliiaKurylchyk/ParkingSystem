package com.kurylchyk.controller.action;

import com.kurylchyk.model.parkingSlots.SizeOfSlot;
import com.kurylchyk.model.services.ParkingTicketService;
import com.kurylchyk.model.services.VehicleService;
import com.kurylchyk.model.services.impl.BusinessServiceFactory;
import com.kurylchyk.model.services.impl.ParkingLotServiceImpl;
import com.kurylchyk.model.vehicles.TypeOfVehicle;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

public class HomeAction implements Action {
    private ParkingTicketService parkingTicketService = new BusinessServiceFactory().forParkingTicket();

    @Override
    public RequestDispatcher execute(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        getVehicleInfo(request);
        getParkingTicketInfo(request);

        request.setAttribute("smallSlots", new ParkingLotServiceImpl().getCountOfSlot(SizeOfSlot.SMALL));
        request.setAttribute("mediumSlots", new ParkingLotServiceImpl().getCountOfSlot(SizeOfSlot.MEDIUM));
        request.setAttribute("largeSlots", new ParkingLotServiceImpl().getCountOfSlot(SizeOfSlot.LARGE));
        RequestDispatcher dispatcher = request.getRequestDispatcher("home.jsp");
        return dispatcher;
        //dispatcher.forward(request, response);
    }


    private void getVehicleInfo(HttpServletRequest req) throws Exception {

        VehicleService vehicleService = new BusinessServiceFactory().forVehicle();

        Integer numberOfBikes = vehicleService.countAllPresent(TypeOfVehicle.MOTORBIKE);
        Integer numberOfCars = vehicleService.countAllPresent(TypeOfVehicle.CAR);
        Integer numberOfTrucks = vehicleService.countAllPresent(TypeOfVehicle.TRUCK);
        Integer numberOfBuses = vehicleService.countAllPresent(TypeOfVehicle.BUS);

        req.setAttribute("numberOfBikes", numberOfBikes);
        req.setAttribute("numberOfCars", numberOfCars);
        req.setAttribute("numberOfTrucks", numberOfTrucks);
        req.setAttribute("numberOfBuses", numberOfBuses);


    }

    private void getParkingTicketInfo(HttpServletRequest req) throws Exception {
        Integer countOfTodayEntities = parkingTicketService.getAllInDate(LocalDateTime.now()).size();
        Integer countOfAllLeft = parkingTicketService.getAll("left").size();
        Integer countOfAllPresent = parkingTicketService.getAll("present").size();
        req.setAttribute("countOfTodayEntities", countOfTodayEntities);
        req.setAttribute("countOfAllPresent", countOfAllPresent);
        req.setAttribute("countOfAllLeft", countOfAllLeft);
    }


}
