package com.kurylchyk.model.services.impl.ticketCommand;

import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.services.CustomerService;
import com.kurylchyk.model.services.VehicleService;
import com.kurylchyk.model.services.impl.Command;
import com.kurylchyk.model.domain.customer.Customer;
import com.kurylchyk.model.dao.ParkingTicketDAO;
import com.kurylchyk.model.domain.parkingTicket.ParkingTicket;
import com.kurylchyk.model.services.impl.ServiceFacade;
import com.kurylchyk.model.domain.vehicles.Vehicle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Deletes ticket completely  and appropriate
 * information about customer and vehicle from database.
 */

public class DeleteTicketCompletely implements Command<Void> {

    private ParkingTicketDAO parkingTicketDAO = new ParkingTicketDAO();
    private VehicleService vehicleService = ServiceFacade.forVehicle();
    private CustomerService customerService = ServiceFacade.forCustomer();
    private ParkingTicket parkingTicket;
    private static final Logger logger = LogManager.getLogger(DeleteTicketCompletely.class);

    public  DeleteTicketCompletely(ParkingTicket parkingTicket){
        this.parkingTicket = parkingTicket;
    }


    DeleteTicketCompletely(ParkingTicket parkingTicket,ParkingTicketDAO parkingTicketDAO,
                           VehicleService vehicleService,CustomerService customerService){
        this.parkingTicket = parkingTicket;
        this.vehicleService = vehicleService;
        this.customerService = customerService;
        this.parkingTicketDAO = parkingTicketDAO;
    }

    @Override
    public Void execute()  throws ParkingSystemException {

        parkingTicketDAO.delete(parkingTicket);
        deleteVehicle(parkingTicket.getVehicle());
        deleteCustomer(parkingTicket.getCustomer());
        logger.info("Parking ticket "+parkingTicket.getParkingTicketID() + " was deleted completely");
        return null;
    }

    private void deleteVehicle(Vehicle vehicle) throws ParkingSystemException {
        if(!parkingTicketDAO.selectByVehicleID(vehicle.getLicensePlate()).isPresent()) {
            vehicleService.deleteCompletely(vehicle);
        }
    }
    private  void deleteCustomer (Customer customer) throws ParkingSystemException {

        if(parkingTicketDAO.selectByCustomerID(customer.getCustomerID()).isEmpty()){
            customerService.deleteCompletely(customer);
        }
    }
}
