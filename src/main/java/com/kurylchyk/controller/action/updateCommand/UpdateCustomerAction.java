package com.kurylchyk.controller.action.updateCommand;

import com.kurylchyk.model.customer.Customer;
import com.kurylchyk.model.exceptions.NoSuchParkingTicketException;
import com.kurylchyk.model.parkingTicket.ParkingTicket;
import com.kurylchyk.model.services.CustomerService;
import com.kurylchyk.model.services.ParkingTicketService;
import com.kurylchyk.model.services.impl.BusinessServiceFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class UpdateCustomerAction implements UpdateAction {
    private CustomerService customerService = new BusinessServiceFactory().forCustomer();
    private ParkingTicketService parkingTicketService = new BusinessServiceFactory().forParkingTicket();

    @Override
    public RequestDispatcher update(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        System.out.println("update customer action");
        RequestDispatcher requestDispatcher = null;

        HttpSession session = request.getSession();
        Integer customerID = ((Customer) session.getAttribute("customer")).getCustomerID();

        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String phoneNumber = request.getParameter("phoneNumber");
        Customer customer = Customer.newCustomer()
                .setCustomerID(customerID)
                .setName(name)
                .setSurname(surname)
                .setPhoneNumber(phoneNumber)
                .buildCustomer();

        customerService.update(customer);

        ParkingTicket currentTicket = (ParkingTicket) session.getAttribute("currentTicket");

        if (currentTicket == null) {
            List<ParkingTicket> allFoundTickets = parkingTicketService.getByCustomer(customer.getCustomerID());
            request.setAttribute("appropriateTickets", allFoundTickets);
            request.setAttribute("onlyCurrentCustomer", "");
            requestDispatcher = request.getRequestDispatcher("showAllTickets.jsp");

        } else {
            currentTicket.setCustomer(customer);
            session.setAttribute("currentTicket", currentTicket);
            requestDispatcher = request.getRequestDispatcher("parkingTicketInfo.jsp");
        }

        session.removeAttribute("customer");
        return requestDispatcher;
    }
}
