package com.kurylchyk.model.dao;

import com.kurylchyk.model.Connector;
import com.kurylchyk.model.Customer;
import com.kurylchyk.model.ParkingTicket;
import com.kurylchyk.model.parkingSlots.ParkingSlot;
import com.kurylchyk.model.vehicles.Vehicle;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class ParkingTicketDAO extends Connector implements GetUpdateDAO<ParkingTicket,Integer>,AddDeleteDAO<ParkingTicket> {

    private Connection connection;
    private CustomerDao customerDao;
    private VehicleDao vehicleDao;
    private ParkingSlotDao parkingSlotDao;
    {
        customerDao = new CustomerDao();
        vehicleDao = new VehicleDao();
        parkingSlotDao = new ParkingSlotDao();

    }


    @Override
    public void add(ParkingTicket parkingTicket) {

    }

    @Override
    public void delete(ParkingTicket parkingTicket) {

    }

    @Override
    public ParkingTicket get(Integer id) throws Exception {
        PreparedStatement preparedStatement = null;
        connection = getConnection();
        ParkingTicket parkingTicket = null;
        String query = "SELECT * FROM parking_ticket WHERE parking_ticket_id = ?";
        try{
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet!=null && resultSet.next()) {
                Vehicle currentVehicle = vehicleDao.get(resultSet.getString("vehicle_id"));
                Customer currentCustomer = customerDao.get(resultSet.getInt("customer_id"));
                ParkingSlot currentParkingSlot = parkingSlotDao.get(resultSet.getString("parking_slot_id"));

                parkingTicket = new ParkingTicket(currentVehicle,currentParkingSlot,currentCustomer);
                parkingTicket.setStatus(resultSet.getString("status"));
                LocalDateTime from_time = resultSet.getObject("from_time",LocalDateTime.class);
                parkingTicket.setFrom_time(from_time);
                parkingTicket.setParkingTicketID(id);

            }
        }catch (SQLException exception) {
            exception.printStackTrace();
        }finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }

        return parkingTicket;
    }

    @Override
    public List<ParkingTicket> getAll() {
        return null;
    }

    @Override
    public void update(ParkingTicket parkingTicket, Integer param) {

    }


}
