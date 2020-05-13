package com.kurylchyk.controller;

import com.kurylchyk.model.Customer;
import com.kurylchyk.model.ParkingTicket;
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


@WebServlet("/delete")
public class DeletionServlet extends HttpServlet {
    private ParkingTicketDAO parkingTicketDAO = new ParkingTicketDAO();


    @Override
    public void init() throws ServletException {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doRemove(req,resp);
    }

    public void doRemove(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        ParkingTicket parkingTicket = (ParkingTicket) session.getAttribute("currentTicket");
        if(parkingTicket==null){
            try {
                parkingTicket = parkingTicketDAO.select(Integer.parseInt(req.getParameter("id")));
                session.setAttribute("currentTicket",parkingTicket);
            }catch (NoSuchParkingTicketException exception){
                exception.printStackTrace();
            }
        }
        ParkingTicketManager.removeParkingTicket(parkingTicket);
        session.removeAttribute("toRemove");
        req.getRequestDispatcher("parkingTicketInfo.jsp").forward(req,resp);

    }

}
