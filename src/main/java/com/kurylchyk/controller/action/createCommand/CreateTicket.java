package com.kurylchyk.controller.action.createCommand;

import com.kurylchyk.model.customer.Customer;
import com.kurylchyk.model.parkingSlots.ParkingSlot;
import com.kurylchyk.model.parkingTicket.ParkingTicket;
import com.kurylchyk.model.services.CustomerService;
import com.kurylchyk.model.services.ParkingTicketService;
import com.kurylchyk.model.services.VehicleService;
import com.kurylchyk.model.services.impl.BusinessServiceFactory;
import com.kurylchyk.model.vehicles.Vehicle;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CreateTicket implements Create {
    private ParkingTicketService parkingTicketService = new BusinessServiceFactory().forParkingTicket();
    private VehicleService vehicleService = new BusinessServiceFactory().forVehicle();
    private CustomerService customerService = new BusinessServiceFactory().forCustomer();
    private ParkingTicket parkingTicket;
    private RequestDispatcher requestDispatcher;

    @Override
    public RequestDispatcher create(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        System.out.println("In parking slot creation");
        Vehicle vehicle = (Vehicle) session.getAttribute("vehicle");
        ParkingSlot parkingSlot = (ParkingSlot) session.getAttribute("parkingSlot");
        Customer customer = (Customer) session.getAttribute("customer");

        ParkingTicket parkingTicket = parkingTicketService.createParkingTicket(vehicle, customer, parkingSlot);


        try {
            vehicleService.saveToDB(parkingTicket.getVehicle());
            customer = customerService.saveToDB(parkingTicket.getCustomer());
            vehicleService.connectCustomerToVehicle(vehicle, customer);
            parkingTicket = parkingTicketService.saveToDB(parkingTicket);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        session.removeAttribute("vehicle");
        session.removeAttribute("customer");
        session.removeAttribute("parkingSlot");
        session.setAttribute("currentTicket",parkingTicket);

        return req.getRequestDispatcher("parkingTicketInfo.jsp");



    }
}
