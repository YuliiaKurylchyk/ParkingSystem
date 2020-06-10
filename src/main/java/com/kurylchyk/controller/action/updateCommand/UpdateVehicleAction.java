package com.kurylchyk.controller.action.updateCommand;

import com.kurylchyk.model.parkingTicket.ParkingTicket;
import com.kurylchyk.model.services.ParkingLotService;
import com.kurylchyk.model.services.ParkingTicketService;
import com.kurylchyk.model.services.VehicleService;
import com.kurylchyk.model.services.impl.BusinessServiceFactory;
import com.kurylchyk.model.services.impl.ParkingLotServiceImpl;
import com.kurylchyk.model.vehicles.TypeOfVehicle;
import com.kurylchyk.model.vehicles.Vehicle;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UpdateVehicleAction implements UpdateAction {
    private VehicleService vehicleService = new BusinessServiceFactory().forVehicle();
    private ParkingTicketService parkingTicketService = new BusinessServiceFactory().forParkingTicket();

    @Override
    public RequestDispatcher update(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        System.out.println("In vehicle updation");
        RequestDispatcher requestDispatcher = null;
        HttpSession session = request.getSession();

        Vehicle currentVehicle = ((Vehicle) session.getAttribute("vehicle"));
        System.out.println("Current vehicle " +currentVehicle);

        String make = request.getParameter("make");
        String model = request.getParameter("model");
        String licencePlate = request.getParameter("licencePlate");
        TypeOfVehicle typeOfVehicle = TypeOfVehicle.valueOf(request.getParameter("typeOfVehicle"));

        Vehicle updatedVehicle = Vehicle.newVehicle().setType(typeOfVehicle)
                .setModel(model).setMake(make).setLicencePlate(licencePlate).buildVehicle();
        System.out.println("Updated vehicle " + updatedVehicle);


        ParkingTicket currentTicket = null;
        if(session.getAttribute("currentTicket")==null) {
            currentTicket = parkingTicketService.getByVehicle(currentVehicle.getLicencePlate());
        }else {
            currentTicket = (ParkingTicket) session.getAttribute("currentTicket");
        }

        if (!currentVehicle.getLicencePlate().equals(currentVehicle.getLicencePlate())) {
            currentTicket.setVehicle(updatedVehicle);
            parkingTicketService.update(currentTicket);
        }

        if (currentVehicle.getTypeOfVehicle() != updatedVehicle.getTypeOfVehicle()) {
            ParkingLotService parkingLotService = new ParkingLotServiceImpl();
            parkingLotService.setParkingSlotBack(currentTicket.getParkingSlot());
            currentTicket.setParkingSlot(parkingLotService.getParkingSlot(updatedVehicle.getTypeOfVehicle()));
            parkingTicketService.update(currentTicket);
        }

        vehicleService.update(updatedVehicle, currentVehicle.getLicencePlate());

        currentTicket.setVehicle(updatedVehicle);
        session.setAttribute("currentTicket", currentTicket);
        session.removeAttribute("vehicle");
        return request.getRequestDispatcher("parkingTicketInfo.jsp");

    }
}
