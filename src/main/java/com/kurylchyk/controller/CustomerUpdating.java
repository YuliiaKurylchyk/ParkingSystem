package com.kurylchyk.controller;

import com.kurylchyk.model.Customer;
import com.kurylchyk.model.ParkingTicket;
import com.kurylchyk.model.dao.CustomerDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/customerUpdating")
public class CustomerUpdating  extends HttpServlet {
    private CustomerDao customerDao = new CustomerDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //get customer id by phone number

        HttpSession session = req.getSession();
        Customer customer = null;
        Integer currentID;
        if(session.getAttribute("customer")==null) {
            customer = (Customer)session.getAttribute("customer");
            currentID = customer.getCustomerID();

        }else {
            currentID = ((Customer)session.getAttribute("customer")).getCustomerID();
            System.out.println(currentID);
            String name = req.getParameter("name");
            String surname = req.getParameter("surname");
            String phoneNumber = req.getParameter("phone_number");
             customer = new Customer(currentID,name,surname,phoneNumber);
        }
        customerDao.update(customer,currentID);

        ParkingTicket currentTicket =(ParkingTicket) session.getAttribute("currentTicket");
        if(currentTicket==null) {
           currentTicket = ParkingTicketManager.getTicketByCustomer(customer);
        }

        System.out.println(customer);
        System.out.println(currentTicket);
        currentTicket.setCustomer(customer);
        session.setAttribute("currentTicket",currentTicket);
        session.removeAttribute("customer");
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("parkingTicketInfo.jsp");
        requestDispatcher.forward(req,resp);
    }
}
