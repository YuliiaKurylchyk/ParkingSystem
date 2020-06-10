package com.kurylchyk.model.services.impl.ticketCommand;

import com.kurylchyk.model.services.impl.Command;
import com.kurylchyk.model.customer.Customer;
import com.kurylchyk.model.dao.CustomerDAO;
import com.kurylchyk.model.dao.ParkingTicketDAO;
import com.kurylchyk.model.dao.VehicleDAO;
import com.kurylchyk.model.parkingTicket.ParkingTicket;
import com.kurylchyk.model.vehicles.Vehicle;

public class DeleteTicketCompletely implements Command<Void> {
    private ParkingTicketDAO parkingTicketDAO = new ParkingTicketDAO();
    private VehicleDAO vehicleDAO = new VehicleDAO();
    private CustomerDAO customerDAO = new CustomerDAO();
    private ParkingTicket parkingTicket;

    public  DeleteTicketCompletely(ParkingTicket parkingTicket){
        this.parkingTicket = parkingTicket;
    }

    @Override
    public Void execute() throws Exception {
        parkingTicketDAO.delete(parkingTicket);
        deleteVehicle(parkingTicket.getVehicle());
        deleteCustomer(parkingTicket.getCustomer());
        return null;
    }

    private void deleteVehicle(Vehicle vehicle) {
        vehicleDAO.delete(vehicle);
    }
    private  void deleteCustomer (Customer customer) {

        if(parkingTicketDAO.selectByCustomerID(customer.getCustomerID()).size()==1){
            customerDAO.delete(customer);
        }
    }
}
