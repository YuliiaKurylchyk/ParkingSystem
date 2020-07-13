package com.kurylchyk.model.services.impl.ticketCommand;

import com.kurylchyk.model.domain.customer.Customer;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.domain.parkingSlots.slotEnum.SlotStatus;
import com.kurylchyk.model.services.CustomerService;
import com.kurylchyk.model.services.ParkingSlotService;
import com.kurylchyk.model.services.VehicleService;
import com.kurylchyk.model.services.impl.Command;
import com.kurylchyk.model.dao.ParkingTicketDAO;
import com.kurylchyk.model.domain.parkingTicket.ParkingTicket;
import com.kurylchyk.model.services.impl.ServiceFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SaveParkingTicketCommand implements Command<ParkingTicket> {

    private ParkingTicketDAO parkingTicketDAO = new ParkingTicketDAO();
    private ParkingSlotService parkingLotService = ServiceFacade.forParkingSlot();
    private VehicleService vehicleService = ServiceFacade.forVehicle();
    private CustomerService customerService = ServiceFacade.forCustomer();
    private ParkingTicket parkingTicket;

    private static final Logger logger = LogManager.getLogger(SaveParkingTicketCommand.class);

    public SaveParkingTicketCommand(ParkingTicket parkingTicket) {
        this.parkingTicket = parkingTicket;
    }


    @Override
    public ParkingTicket execute() throws ParkingSystemException {

        vehicleService.saveToDB(parkingTicket.getVehicle());
        parkingLotService.updateStatus(parkingTicket.getParkingSlot(), SlotStatus.OCCUPIED);
        Customer customer = customerService.saveToDB(parkingTicket.getCustomer());
        vehicleService.connectCustomerToVehicle(parkingTicket.getVehicle(), customer);
        Integer parkingTicketID = parkingTicketDAO.insert(parkingTicket);
        parkingTicket.setParkingTicketID(parkingTicketID);
        logger.info("Parking ticket with ID " + parkingTicketID + " was created and saved to database");
        return parkingTicket;
    }
}
