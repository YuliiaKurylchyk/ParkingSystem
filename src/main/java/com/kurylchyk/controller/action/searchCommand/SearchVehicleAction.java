package com.kurylchyk.controller.action.searchCommand;

import com.kurylchyk.model.services.VehicleService;
import com.kurylchyk.model.services.impl.BusinessServiceFactory;
import com.kurylchyk.model.vehicles.Vehicle;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SearchVehicleAction implements Search {

    private VehicleService vehicleService = new BusinessServiceFactory().forVehicle();


    @Override
    public RequestDispatcher search(HttpServletRequest req, HttpServletResponse resp) {

        Vehicle vehicle = null;
        String licencePlate = req.getParameter("vehicleID");
        try {
            vehicle = vehicleService.getFromDB(licencePlate);
        } catch (Exception exception) {
            req.setAttribute("notFound", exception);
            return req.getRequestDispatcher("searchPage.jsp");
        }

            HttpSession session = req.getSession();
            session.setAttribute("vehicle", vehicle);

            return defineAction(req, resp);
        }

        private RequestDispatcher defineAction (HttpServletRequest req, HttpServletResponse resp)
        {
            RequestDispatcher requestDispatcher = null;
            String action = String.valueOf(req.getSession().getAttribute("action"));
            switch (action) {
                case "update":
                    requestDispatcher = req.getRequestDispatcher("vehicleRegistration.jsp");
                    break;
                case "deleting":
                    req.getRequestDispatcher("delete?action=remove");
                    break;
            }
            return requestDispatcher;
        }
    }
