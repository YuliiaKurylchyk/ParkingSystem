package com.kurylchyk.controller;

import com.kurylchyk.model.Customer;
import com.kurylchyk.model.ParkingTicket;
import com.kurylchyk.model.dao.ParkingSlotDao;
import com.kurylchyk.model.dao.ParkingTicketDAO;
import com.kurylchyk.model.exceptions.NoSuchParkingTicketException;
import com.kurylchyk.model.parkingSlots.LargeSlot;
import com.kurylchyk.model.vehicles.Vehicle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet("/deletingServlet")
public class DeletionServlet extends HttpServlet {
    private ParkingTicketDAO parkingTicketDAO = new ParkingTicketDAO();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getParameter("action");
        retrieveParkingTicket(req,resp);
        switch (action){
            case "remove":
                doRemove(req,resp);
                break;
            case "delete":
                doDeleteCompletely(req,resp);
                break;
        }
    }

    public void doRemove(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        ParkingTicket parkingTicket = (ParkingTicket) session.getAttribute("currentTicket");

        if(parkingTicket.getStatus().equals("left")){
            req.setAttribute("alreadyLeft","The parking ticket status is already left");
        }else {
            ParkingTicketManager.removeParkingTicket(parkingTicket);
        }
        session.setAttribute("currentTicket", parkingTicket);
        req.getRequestDispatcher("parkingTicketInfo.jsp").forward(req, resp);

    }

    public void doDeleteCompletely(HttpServletRequest req, HttpServletResponse resp){


    }


    private void retrieveParkingTicket(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session.getAttribute("currentTicket") == null) {
            ParkingTicket currentTicket = null;
            if (session.getAttribute("vehicle") != null) {
                currentTicket = ParkingTicketManager.getTicketByVehicle((Vehicle) session.getAttribute("vehicle"));
                session.removeAttribute("vehicle");
            }
            if (session.getAttribute("customer") != null) {
                currentTicket = ParkingTicketManager.getTicketByCustomer((Customer) session.getAttribute("customer"));
                session.removeAttribute("customer");
            }
            if(req.getParameter("parkingTicketID")!=null){
                currentTicket = ParkingTicketManager.getTicketByID(Integer.parseInt(req.getParameter("parkingTicketID")));
            }
            session.setAttribute("currentTicket", currentTicket);
        }
    }
}
