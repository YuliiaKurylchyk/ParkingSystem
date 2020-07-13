package com.kurylchyk.controller;

import com.kurylchyk.model.services.impl.parkingSlotDTO.ParkingSlotDTO;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.domain.parkingSlots.ParkingSlot;
import com.kurylchyk.model.domain.parkingSlots.slotEnum.SlotSize;
import com.kurylchyk.model.domain.parkingTicket.ParkingTicket;
import com.kurylchyk.model.services.ParkingSlotService;
import com.kurylchyk.model.services.impl.ParkingSlotServiceImpl;
import com.kurylchyk.model.domain.vehicles.Vehicle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/parkingSlot/*")
public class ParkingSlotServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(ParkingSlotServlet.class);
    private ParkingSlotService parkingLotService = new ParkingSlotServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {

        String command = req.getPathInfo();

        try {
            switch (command) {
                case "/showAvailable":
                    doShowAvailable(req, resp);
                    break;
                case "/get":
                    doGetParkingSlot(req, resp);
                    break;
                    //check if it is used somewhere
                case "/edit":
                   // doEdit(req, resp);
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
        }catch (Exception exception){
            logger.error(exception);
        }
    }


    protected void doGetParkingSlot(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Integer parkingSlotID = Integer.parseInt(req.getParameter("parkingSlotID"));
        SlotSize slotSize = SlotSize.valueOf(req.getParameter("slotSize"));

        try {

            ParkingSlot parkingSlot = parkingLotService.getParkingSlot(new ParkingSlotDTO(slotSize, parkingSlotID));
            req.getSession().setAttribute("parkingSlot", parkingSlot);
            req.getRequestDispatcher("/customer/form").forward(req, resp);
        } catch (ParkingSystemException exception) {
            logger.error(exception);
        }
    }

    protected void doShowAvailable(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        List<ParkingSlot> allAvailableSlots;
        try {
            if (req.getSession().getAttribute("vehicle") != null) {
                Vehicle vehicle = (Vehicle) req.getSession().getAttribute("vehicle");
                allAvailableSlots = parkingLotService.getAvailableSlots(vehicle);
            } else {
                SlotSize slotSize = SlotSize.valueOf(req.getParameter("slotSize"));
                allAvailableSlots = parkingLotService.getAvailableSlots(slotSize);
                req.setAttribute("change", "");
            }
            req.setAttribute("allSlots", allAvailableSlots);
            req.getRequestDispatcher("/showAllSlots.jsp").forward(req, resp);
        } catch (ParkingSystemException exception) {
            logger.error(exception);
            req.getSession().removeAttribute("vehicle");
            req.setAttribute("exception", exception);
            req.getRequestDispatcher("/errorPage.jsp").forward(req, resp);
        }
    }

    protected void doShowAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


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
        } catch (ParkingSystemException exception) {
            logger.error(exception);
        }

    }

    protected void doChange(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Integer parkingSlotID = Integer.parseInt(req.getParameter("parkingSlotID"));
        SlotSize slotSize = SlotSize.valueOf(req.getParameter("slotSize"));

        try {
            ParkingSlot parkingSlot = parkingLotService.getParkingSlot(new ParkingSlotDTO(slotSize, parkingSlotID));
            ParkingTicket parkingTicket = (ParkingTicket) req.getSession().getAttribute("currentTicket");
            parkingLotService.changeSlot(parkingTicket, parkingSlot);
            req.getRequestDispatcher("/parkingTicket/get?parkingTicketID=" + parkingTicket.getParkingTicketID()).forward(req, resp);
        } catch (ParkingSystemException exception) {
            logger.error(exception);
        }

    }


}
