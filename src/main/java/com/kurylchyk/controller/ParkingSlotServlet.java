package com.kurylchyk.controller;

import com.kurylchyk.model.dao.ParkingSlotDTO;
import com.kurylchyk.model.parkingSlots.ParkingSlot;
import com.kurylchyk.model.parkingSlots.SlotSize;
import com.kurylchyk.model.parkingSlots.SlotStatus;
import com.kurylchyk.model.parkingTicket.ParkingTicket;
import com.kurylchyk.model.services.ParkingSlotService;
import com.kurylchyk.model.services.ParkingSlotPriceDTO;
import com.kurylchyk.model.services.impl.ParkingSlotServiceImpl;
import com.kurylchyk.model.vehicles.Vehicle;

import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/parkingSlot/*")
public class ParkingSlotServlet extends HttpServlet {
    private ParkingSlotService parkingLotService = new ParkingSlotServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        System.out.println("In parking slot servlet");
        String command = req.getPathInfo();

        switch (command) {
            case "/showAvailable":
                doShowAvailable(req, resp);
                break;
            case "/get":
                doGetParkingSlot(req, resp);
                break;
            case "/edit":
                doEdit(req, resp);
                break;
            case "/update":
                //  doUpdate(req, resp);
                break;
            case "/showAll":
                doShowAll(req, resp);
                break;
            case "/change":
                doChange(req, resp);
                break;



        }
    }


    protected void doGetParkingSlot(HttpServletRequest req, HttpServletResponse resp) {

        Integer parkingSlotID = Integer.parseInt(req.getParameter("parkingSlotID"));
        SlotSize slotSize = SlotSize.valueOf(req.getParameter("slotSize"));

        try {

            ParkingSlot parkingSlot = parkingLotService.getParkingSlot(new ParkingSlotDTO(slotSize, parkingSlotID));
            req.getSession().setAttribute("parkingSlot", parkingSlot);
            req.getRequestDispatcher("/customer/form").forward(req, resp);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    protected void doShowAvailable(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        List<ParkingSlot> allAvailableSlots;
        try {
            if (req.getSession().getAttribute("vehicle") != null) {
                Vehicle vehicle = (Vehicle) req.getSession().getAttribute("vehicle");
                allAvailableSlots = parkingLotService.getAvailableSlots(vehicle);
            }else{
                SlotSize slotSize = SlotSize.valueOf(req.getParameter("slotSize"));
                allAvailableSlots = parkingLotService.getAvailableSlots(slotSize);
                req.setAttribute("change","");
            }
            req.setAttribute("allSlots", allAvailableSlots);
            req.getRequestDispatcher("/showAllSlots.jsp").forward(req, resp);
        } catch (Exception exception) {
            System.out.println("Exception  is parking slot show available");
            exception.printStackTrace();
            req.getSession().removeAttribute("vehicle");
            req.setAttribute("exception", exception);
            req.getRequestDispatcher("/errorPage.jsp").forward(req, resp);
            return;
        }
    }

    protected void doShowAll(HttpServletRequest req, HttpServletResponse resp) {


        String size = req.getParameter("slotSize");
        List<ParkingSlot> allSlots;

        try {
            if (size.equalsIgnoreCase("all")) {
                allSlots = parkingLotService.getAll();
            } else {
                SlotSize slotSize = SlotSize.valueOf(size);
                allSlots = parkingLotService.getAll(slotSize);
            }
            req.setAttribute("allSlots", allSlots);
            req.getRequestDispatcher("/showAllSlots.jsp?info").forward(req, resp);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    protected void doChange(HttpServletRequest req, HttpServletResponse resp) {

        System.out.println("In do change servlet");
        Integer parkingSlotID = Integer.parseInt(req.getParameter("parkingSlotID"));
        SlotSize slotSize = SlotSize.valueOf(req.getParameter("slotSize"));

        try {
            ParkingSlot parkingSlot = parkingLotService.getParkingSlot(new ParkingSlotDTO(slotSize,parkingSlotID));
            ParkingTicket parkingTicket = (ParkingTicket) req.getSession().getAttribute("currentTicket");
            parkingLotService.changeSlot(parkingTicket,parkingSlot);
            req.getRequestDispatcher("/parkingTicket/get?parkingTicketID="+parkingTicket.getParkingTicketID()).forward(req,resp);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    protected void doEdit(HttpServletRequest req, HttpServletResponse resp) {

        try {
            List<ParkingSlotPriceDTO> allSlots = parkingLotService.getSlotsPrice();
            System.out.println(allSlots);
            req.setAttribute("allSlots", allSlots);
            req.getRequestDispatcher("/parkingSlotInfo.jsp").forward(req, resp);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

}
