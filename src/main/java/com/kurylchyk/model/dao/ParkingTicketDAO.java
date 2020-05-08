package com.kurylchyk.model.dao;

import com.kurylchyk.model.Connector;
import com.kurylchyk.model.Customer;
import com.kurylchyk.model.ParkingTicket;
import com.kurylchyk.model.exceptions.NoSuchVehicleFoundException;
import com.kurylchyk.model.parkingSlots.ParkingSlot;
import com.kurylchyk.model.parkingSlots.SmallSlot;
import com.kurylchyk.model.vehicles.Motorbike;
import com.kurylchyk.model.vehicles.Vehicle;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class ParkingTicketDAO extends Connector implements GetUpdateDAO<ParkingTicket, Integer>, AddDeleteDAO<ParkingTicket, Integer> {

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
    public Integer insert(ParkingTicket parkingTicket) {
        connection = getConnection();
        PreparedStatement preparedStatement = null;
        String query = "INSERT INTO parking_ticket(customer_id,vehicle_id,parking_slot_id,status,from_time)" +
                "VALUES (?,?,?,?,?)";
        Integer id = null;
        try {

            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, parkingTicket.getCustomer().getCustomerID());
            preparedStatement.setString(2, parkingTicket.getVehicle().getLicencePlate());
            preparedStatement.setInt(3, parkingTicket.getParkingSlot().getParkingSlotID());
            preparedStatement.setString(4, parkingTicket.getStatus());
            preparedStatement.setObject(5, parkingTicket.getFrom_time());
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
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

        return id;
    }

    @Override
    public void delete(ParkingTicket parkingTicket) {
    }

    @Override
    public ParkingTicket select(Integer id)  {
        PreparedStatement preparedStatement = null;
        connection = getConnection();
        ParkingTicket parkingTicket = null;
        String query = "SELECT * FROM parking_ticket WHERE parking_ticket_id = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet != null && resultSet.next()) {
                Vehicle currentVehicle = vehicleDao.select(resultSet.getString("vehicle_id"));
                Customer currentCustomer = customerDao.select(resultSet.getInt("customer_id"));
                ParkingSlot currentParkingSlot = parkingSlotDao.select(resultSet.getInt("parking_slot_id"));

                parkingTicket = new ParkingTicket(currentVehicle, currentParkingSlot, currentCustomer);
                parkingTicket.setStatus(resultSet.getString("status"));
                LocalDateTime from_time = resultSet.getObject("from_time", LocalDateTime.class);
                parkingTicket.setFrom_time(from_time);
                parkingTicket.setParkingTicketID(id);

            }
        } catch (SQLException | NoSuchVehicleFoundException exception) {
            exception.printStackTrace();
        } finally {
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
    public List<ParkingTicket> selectAll() {
        return null;
    }

    public List<ParkingTicket> selectInDate(LocalDateTime date) {

        connection = getConnection();
        PreparedStatement preparedStatement = null;
        String query = "SELECT * FROM parking_ticket WHERE from_time >= ?";
        LinkedList<ParkingTicket> listOfTickets = new LinkedList<>();

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setObject(1, date);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                //refactor a little
                ParkingTicket current = new ParkingTicket();
                Integer id = resultSet.getInt("parking_ticket_id");
                Vehicle vehicle = vehicleDao.select(resultSet.getString("vehicle_id"));
                Customer customer = customerDao.select(resultSet.getInt("customer_id"));
                ParkingSlot parkingSlot = parkingSlotDao.select(resultSet.getInt("parking_slot_id"));
                String status = resultSet.getString("status");
                LocalDateTime from_time = resultSet.getObject("from_time", LocalDateTime.class);
                current.setParkingTicketID(id);
                current.setVehicle(vehicle);
                current.setCustomer(customer);
                current.setParkingSlot(parkingSlot);
                current.setStatus(status);
                current.setFrom_time(from_time);
                listOfTickets.add(current);
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        } catch (NoSuchVehicleFoundException ex) {
            ex.printStackTrace();
        } finally {

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

        return listOfTickets;
    }

    @Override
    public void update(ParkingTicket parkingTicket, Integer param) {

    }


}
