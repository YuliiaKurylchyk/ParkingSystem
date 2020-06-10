package com.kurylchyk.controller;

import com.kurylchyk.controller.action.CreateAction;
import com.kurylchyk.model.exceptions.NoAvailableParkingSlotException;
import com.kurylchyk.model.parkingTicket.ParkingTicket;
import com.kurylchyk.model.exceptions.SuchVehiclePresentException;
import com.kurylchyk.model.parkingTicket.ParkingTicketBuilder;
import com.kurylchyk.model.vehicles.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/creationServlet")
public class CreationServlet extends HttpServlet {



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        RequestDispatcher requestDispatcher = null;
        try {
            requestDispatcher = new CreateAction().execute(req,resp);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        requestDispatcher.forward(req,resp);
    }



}
