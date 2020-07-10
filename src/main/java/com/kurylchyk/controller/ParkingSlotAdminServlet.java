package com.kurylchyk.controller;

import com.kurylchyk.model.services.impl.parkingSlotDTOs.ParkingSlotDTO;
import com.kurylchyk.model.domain.parkingSlots.ParkingSlot;
import com.kurylchyk.model.domain.parkingSlots.slotEnum.SlotSize;
import com.kurylchyk.model.domain.parkingSlots.slotEnum.SlotStatus;
import com.kurylchyk.model.services.impl.parkingSlotDTOs.ParkingSlotPriceDTO;
import com.kurylchyk.model.services.ParkingSlotService;
import com.kurylchyk.model.services.impl.ServiceFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@WebServlet("/admin/*")
public class ParkingSlotAdminServlet extends HttpServlet {

    private ParkingSlotService parkingSlotService = ServiceFacade.forParkingSlot();
    private static final Logger logger = LogManager.getLogger(ParkingSlotAdminServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        doPost(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {


        String command = req.getPathInfo();
        try {
            if (command == null) {
                showPage(req, resp);
            } else {

                switch (command) {

                    case "/editPrice":
                        doEditPrice(req, resp);
                        break;
                    case "/addSlot":
                        doAddSlot(req, resp);
                        break;
                    case "/deleteSlot":
                        doDeleteSlot(req, resp);
                        break;
                }
            }
        }catch (Exception exception){
            logger.error(exception);
        }

    }


    protected void showPage(HttpServletRequest req, HttpServletResponse resp)  {
        try {
            List<ParkingSlotPriceDTO> slotPrices = parkingSlotService.getSlotsPrice();
            System.out.println(slotPrices);
            req.setAttribute("slotPrices", slotPrices);

            List<ParkingSlot> allSlots = parkingSlotService.getAll();
            req.setAttribute("allSlots", allSlots);
            req.getRequestDispatcher("/parkingSlotInfo.jsp").forward(req, resp);
        } catch (Exception exception) {
            logger.error(exception);
        }
    }

    protected void doAddSlot(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        SlotSize slotSize = SlotSize.valueOf(req.getParameter("newSlotSize"));
        SlotStatus slotStatus = SlotStatus.valueOf(req.getParameter("newSlotStatus"));
        try {
            parkingSlotService.addSlot(slotSize, slotStatus);
            showPage(req, resp);
        } catch (Exception exception) {
            logger.error(exception);
        }
    }


    protected void doEditPrice(HttpServletRequest req, HttpServletResponse resp) {

        List<ParkingSlotPriceDTO> prices = new ArrayList();
        prices.add(new ParkingSlotPriceDTO(SlotSize.valueOf(req.getParameter("SMALL")),
                Integer.parseInt(req.getParameter("priceOfSMALL"))));
        prices.add(new ParkingSlotPriceDTO(SlotSize.valueOf(req.getParameter("MEDIUM")),
                Integer.parseInt(req.getParameter("priceOfMEDIUM"))));
        prices.add(new ParkingSlotPriceDTO(SlotSize.valueOf(req.getParameter("LARGE")),
                Integer.parseInt(req.getParameter("priceOfLARGE"))));

        try {
            parkingSlotService.updatePrice(prices);
            req.setAttribute("saved", "Changes were saved");
            showPage(req, resp);
        } catch (Exception exception) {
            logger.error(exception);
        }

    }


    protected void doDeleteSlot(HttpServletRequest req, HttpServletResponse resp) {
        Integer parkingSlotID = Integer.parseInt(req.getParameter("parkingSlotID"));
        SlotSize slotSize = SlotSize.valueOf(req.getParameter("slotSize"));
        try {
            ParkingSlot parkingSlot = parkingSlotService.getParkingSlot(new ParkingSlotDTO(slotSize, parkingSlotID));
            parkingSlotService.deleteSlot(parkingSlot);
            showPage(req, resp);
        } catch (Exception exception) {
            logger.error(exception);
        }

    }
}
