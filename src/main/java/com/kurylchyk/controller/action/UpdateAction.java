package com.kurylchyk.controller.action;

import com.kurylchyk.controller.action.Action;
import com.kurylchyk.controller.action.updateCommand.UpdateCustomerAction;
import com.kurylchyk.controller.action.updateCommand.UpdateVehicleAction;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UpdateAction implements Action {
    @Override
    public RequestDispatcher execute(HttpServletRequest request, HttpServletResponse response) throws Exception {


        System.out.println("In updating servlet");
        RequestDispatcher requestDispatcher = null;

        HttpSession session = request.getSession();

        if(session.getAttribute("vehicle")!=null) {
            System.out.println("attribute from session " +session.getAttribute("vehicle"));
            requestDispatcher = new UpdateVehicleAction().update(request, response);

        } else if(session.getAttribute("customer")!=null){
            System.out.println("attribute from session " +session.getAttribute("customer"));
                requestDispatcher = new UpdateCustomerAction().update(request, response);
        }


        return requestDispatcher;
    }
}
