package com.kurylchyk.controller;

import com.kurylchyk.model.Customer;
import com.kurylchyk.model.dao.CustomerDao;
import com.kurylchyk.model.dao.VehicleDao;
import com.kurylchyk.model.exceptions.NoSuchCustomerFoundException;
import com.kurylchyk.model.exceptions.NoSuchVehicleFoundException;
import com.kurylchyk.model.vehicles.Vehicle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet("/editTicket")
public class EditionServlet extends HttpServlet {
    private VehicleDao vehicleDao;
    private CustomerDao customerDao;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        RequestDispatcher requestDispatcher;
        if (req.getParameter("vehicleID") != null) {
            try {
                retrieveVehicle(req, resp);
                requestDispatcher = req.getRequestDispatcher("vehicleRegistration.jsp");
                requestDispatcher.forward(req,resp);
            }catch (NoSuchVehicleFoundException ex){
                //redirect to isErrorPage
            }
        }
        if (req.getParameter("customerID") != null) {
            retrieveCustomer(req,resp);
            requestDispatcher = req.getRequestDispatcher("customerRegistration.jsp");
            requestDispatcher.forward(req,resp);
        }
    }

    private void retrieveVehicle(HttpServletRequest req,HttpServletResponse resp) throws NoSuchVehicleFoundException {

        vehicleDao = new VehicleDao();
        Vehicle vehicle = vehicleDao.select(req.getParameter("vehicleID"));
        HttpSession session = req.getSession();
        session.setAttribute("vehicle",vehicle);

    }

    private void retrieveCustomer(HttpServletRequest req,HttpServletResponse resp) {

        customerDao = new CustomerDao();
        try {
            Customer customer = customerDao.select(Integer.parseInt(req.getParameter("customerID")));
            System.out.println(customer);

            HttpSession session = req.getSession();
            session.setAttribute("customer", customer);

        }catch (NoSuchCustomerFoundException exception){
            //do something
        }
    }


}
