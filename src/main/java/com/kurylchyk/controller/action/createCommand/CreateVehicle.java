package com.kurylchyk.controller.action.createCommand;

import com.kurylchyk.model.exceptions.NoAvailableParkingSlotException;
import com.kurylchyk.model.exceptions.SuchVehiclePresentException;
import com.kurylchyk.model.parkingSlots.ParkingSlot;
import com.kurylchyk.model.services.VehicleService;
import com.kurylchyk.model.services.impl.BusinessServiceFactory;
import com.kurylchyk.model.services.impl.ParkingLotServiceImpl;
import com.kurylchyk.model.vehicles.TypeOfVehicle;
import com.kurylchyk.model.vehicles.Vehicle;

import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateVehicle implements Create {
    private VehicleService vehicleService= new BusinessServiceFactory().forVehicle();
    private RequestDispatcher requestDispatcher;
    private Vehicle vehicle;
    @Override
    public RequestDispatcher create(HttpServletRequest req, HttpServletResponse resp) {
        String typeOfVehicle = req.getParameter("typeOfVehicle");
        String make = req.getParameter("make");
        String model = req.getParameter("model");
        String licencePlate = req.getParameter("licencePlate");

        try{
            List<String> violations = vehicleService.validate(make,model,licencePlate,typeOfVehicle);
            if(!violations.isEmpty()){
                setAttributeBack(typeOfVehicle,make,model,licencePlate,req);
                req.setAttribute("violations", violations);
               requestDispatcher =  req.getRequestDispatcher("vehicleRegistration.jsp");
               return requestDispatcher;
            }
            if(vehicleService.isPresent(licencePlate)){
                if(vehicleService.getVehicleStatus(licencePlate).equals("present")) {
                    req.setAttribute("exception", new SuchVehiclePresentException("Such vehicle with licence plate"
                            + licencePlate + " is already present"));
                    return req.getRequestDispatcher("errorPage.jsp");
                }else {
                    vehicle = vehicleService.getFromDB(licencePlate);

                }
            }else {
                vehicle =vehicleService.create(make,model,licencePlate, TypeOfVehicle.valueOf(typeOfVehicle));

            }

        }catch (Exception exception){
            exception.printStackTrace();
        }

        ParkingSlot parkingSlot;
        try{
            parkingSlot = getAppropriateSlot(vehicle.getTypeOfVehicle());
        }catch (NoAvailableParkingSlotException exception){
            req.setAttribute("exception",exception);
            return req.getRequestDispatcher("errorPage.jsp");
        }
        req.getSession().setAttribute("vehicle",vehicle);
        req.getSession().setAttribute("parkingSlot",parkingSlot);
        requestDispatcher = req.getRequestDispatcher("customerRegistration.jsp");
        return requestDispatcher;
    }

    private void setAttributeBack(String typeOfVehicle,String make,String model,String licencePlate,HttpServletRequest req){
        req.setAttribute("typeOfVehicle",typeOfVehicle);
        req.setAttribute("make",make);
        req.setAttribute("model",model);
        req.setAttribute("licencePlate",licencePlate);
    }

    private ParkingSlot getAppropriateSlot(TypeOfVehicle typeOfVehicle) throws NoAvailableParkingSlotException {

        return  new ParkingLotServiceImpl().getParkingSlot(typeOfVehicle);
    }

}
