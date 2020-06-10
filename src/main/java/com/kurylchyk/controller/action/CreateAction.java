package com.kurylchyk.controller.action;

import com.kurylchyk.controller.action.createCommand.CreateCustomer;
import com.kurylchyk.controller.action.createCommand.CreateTicket;
import com.kurylchyk.controller.action.createCommand.CreateVehicle;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateAction implements Action{


    @Override
    public RequestDispatcher execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        RequestDispatcher requestDispatcher = null;

        switch (req.getParameter("action")) {

            case "vehicle":
                requestDispatcher = new CreateVehicle().create(req,resp);
                requestDispatcher.forward(req,resp);
                break;
            case "customer":
                new CreateCustomer().create(req,resp);
                requestDispatcher = new CreateTicket().create(req,resp);
                break;
            default:
                requestDispatcher = req.getRequestDispatcher("vehicleRegistration.jsp");
        }
        return requestDispatcher;
    }
}
