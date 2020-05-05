package com.kurylchyk.controller;

import com.kurylchyk.model.dao.CustomerDao;
import com.kurylchyk.model.dao.ParkingSlotDao;
import com.kurylchyk.model.dao.ParkingTicketDAO;
import com.kurylchyk.model.dao.VehicleDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/showEntity")
public class ShowEntity extends HttpServlet {

    private ParkingTicketDAO parkingTicketDAO ;
    private CustomerDao customerDao;
    private VehicleDao vehicleDao;
    private ParkingSlotDao parkingSlotDao;

    @Override
    public void init() throws ServletException {

        ParkingTicketDAO parkingTicketDAO = new ParkingTicketDAO() ;
         CustomerDao customerDao = new CustomerDao();
        VehicleDao vehicleDao = new VehicleDao();
        parkingTicketDAO = new ParkingTicketDAO();

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        String action = req.getParameter("entityName");
        HttpSession session = req.getSession();

        switch (action) {
            case "parkingTicket" :
                session.setAttribute("entityList",parkingTicketDAO.selectAll());
                break;
            case "vehicle":
                session.setAttribute("entityList",vehicleDao.selectAll());
                break;
            case "customer":
                session.setAttribute("entityList",customerDao.selectAll());
                break;
            case "parkingSlot":
                session.setAttribute("entityList",parkingSlotDao.selectAll());
            break;
        }

    }

}
