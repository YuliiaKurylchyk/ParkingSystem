package com.kurylchyk.controller.action;

import com.kurylchyk.controller.action.searchCommand.SearchCustomerAction;
import com.kurylchyk.controller.action.searchCommand.SearchTicketAction;
import com.kurylchyk.controller.action.searchCommand.SearchVehicleAction;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SearchAction implements Action {


    @Override
    public RequestDispatcher execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        RequestDispatcher requestDispatcher = null;

        System.out.println("in search action");
        switch (request.getParameter("option")) {
            case "parkingTicket":
                requestDispatcher = new SearchTicketAction().search(request, response);
                break;
            case "customer":
                requestDispatcher = new SearchCustomerAction().search(request, response);
                break;
            case "vehicle":
                requestDispatcher = new SearchVehicleAction().search(request, response);
                break;
        }

        return requestDispatcher;
    }
}
