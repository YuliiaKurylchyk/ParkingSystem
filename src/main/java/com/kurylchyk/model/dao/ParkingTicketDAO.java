package com.kurylchyk.model.dao;

import com.kurylchyk.model.Connector;
import com.kurylchyk.model.Customer;
import com.kurylchyk.model.ParkingTicket;
import com.kurylchyk.model.exceptions.NoSuchCustomerFoundException;
import com.kurylchyk.model.exceptions.NoSuchParkingTicketException;
import com.kurylchyk.model.exceptions.NoSuchVehicleFoundException;
import com.kurylchyk.model.parkingSlots.ParkingSlot;
import com.kurylchyk.model.parkingSlots.SmallSlot;
import com.kurylchyk.model.vehicles.Motorbike;
import com.kurylchyk.model.vehicles.Vehicle;

import java.math.BigDecimal;
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
            preparedStatement.setObject(5, parkingTicket.getArrivalTime());
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
    public ParkingTicket select(Integer id) throws NoSuchParkingTicketException {
        PreparedStatement preparedStatement = null;
        connection = getConnection();
        ParkingTicket parkingTicket = null;
        String query = "SELECT * FROM parking_ticket WHERE parking_ticket_id = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Vehicle currentVehicle = vehicleDao.select(resultSet.getString("vehicle_id"));
                Customer currentCustomer = customerDao.select(resultSet.getInt("customer_id"));
                ParkingSlot currentParkingSlot = parkingSlotDao.select(resultSet.getInt("parking_slot_id"));

                parkingTicket = new ParkingTicket(currentVehicle, currentParkingSlot, currentCustomer);
                parkingTicket.setStatus(resultSet.getString("status"));
                LocalDateTime arrivalTime = resultSet.getObject("from_time", LocalDateTime.class);
                LocalDateTime leftTime = resultSet.getObject("to_time",LocalDateTime.class);
                parkingTicket.setArrivalTime(arrivalTime);
                parkingTicket.setLeftTime(leftTime);
                parkingTicket.setCost(resultSet.getBigDecimal("cost"));
                parkingTicket.setParkingTicketID(id);
            } else {
                throw new NoSuchParkingTicketException("Parking ticket with id " + id + "is found");
            }
        } catch (SQLException | NoSuchVehicleFoundException | NoSuchCustomerFoundException exception) {
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

    public ParkingTicket selectByVehicleID(String vehicleID) {
        PreparedStatement preparedStatement = null;
        connection = getConnection();
        ParkingTicket parkingTicket = null;
        String query = "SELECT * FROM parking_ticket WHERE vehicle_id = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, vehicleID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Vehicle currentVehicle = vehicleDao.select(resultSet.getString("vehicle_id"));
                Customer currentCustomer = customerDao.select(resultSet.getInt("customer_id"));
                ParkingSlot currentParkingSlot = parkingSlotDao.select(resultSet.getInt("parking_slot_id"));
                parkingTicket = new ParkingTicket(currentVehicle, currentParkingSlot, currentCustomer);
                parkingTicket.setStatus(resultSet.getString("status"));
                LocalDateTime arrivalTime = resultSet.getObject("from_time", LocalDateTime.class);
                parkingTicket.setArrivalTime(arrivalTime);
                LocalDateTime leftTime = resultSet.getObject("to_time", LocalDateTime.class);
                parkingTicket.setLeftTime(leftTime);
                parkingTicket.setCost(resultSet.getBigDecimal("cost"));
                parkingTicket.setParkingTicketID(resultSet.getInt("parking_ticket_id"));
            } else {
                throw new NoSuchParkingTicketException("Parking ticket with licence plate " + vehicleID + "is found");
            }
        } catch (SQLException | NoSuchVehicleFoundException | NoSuchCustomerFoundException | NoSuchParkingTicketException exception) {
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

    public ParkingTicket selectByCustomerID(Integer customerID) {

        PreparedStatement preparedStatement = null;
        connection = getConnection();
        ParkingTicket parkingTicket = null;
        String query = "SELECT * FROM parking_ticket WHERE customer_id = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, customerID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Vehicle currentVehicle = vehicleDao.select(resultSet.getString("vehicle_id"));
                Customer currentCustomer = customerDao.select(resultSet.getInt("customer_id"));
                ParkingSlot currentParkingSlot = parkingSlotDao.select(resultSet.getInt("parking_slot_id"));
                parkingTicket = new ParkingTicket(currentVehicle, currentParkingSlot, currentCustomer);
                parkingTicket.setStatus(resultSet.getString("status"));
                LocalDateTime arrivalTime = resultSet.getObject("from_time", LocalDateTime.class);
                parkingTicket.setArrivalTime(arrivalTime);
                LocalDateTime leftTime = resultSet.getObject("to_time", LocalDateTime.class);
                parkingTicket.setLeftTime(leftTime);
                parkingTicket.setCost(resultSet.getBigDecimal("cost"));
                parkingTicket.setParkingTicketID(resultSet.getInt("parking_ticket_id"));
            } else {
                throw new NoSuchParkingTicketException("Parking ticket with customer id " + customerID + "is found");
            }
        } catch (SQLException | NoSuchVehicleFoundException | NoSuchCustomerFoundException | NoSuchParkingTicketException exception) {
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

    //make it array list
    @Override
    public List<ParkingTicket> selectAll() {
        connection = getConnection();
        PreparedStatement preparedStatement = null;
        String query = "SELECT * FROM parking_ticket";
        LinkedList<ParkingTicket> listOfTickets = new LinkedList<>();

        try {
            preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                //refactor a little
                //you may create separate method that takes resultSet;
                ParkingTicket current = new ParkingTicket();
                Integer id = resultSet.getInt("parking_ticket_id");
                Vehicle vehicle = vehicleDao.select(resultSet.getString("vehicle_id"));
                Customer customer = customerDao.select(resultSet.getInt("customer_id"));
                ParkingSlot parkingSlot = parkingSlotDao.select(resultSet.getInt("parking_slot_id"));
                String status = resultSet.getString("status");
                LocalDateTime arrivalTime = resultSet.getObject("from_time", LocalDateTime.class);
                LocalDateTime leftTime = resultSet.getObject("to_time", LocalDateTime.class);
                BigDecimal cost = resultSet.getBigDecimal("cost");

                current.setParkingTicketID(id);
                current.setVehicle(vehicle);
                current.setCustomer(customer);
                current.setParkingSlot(parkingSlot);
                current.setStatus(status);
                current.setArrivalTime(arrivalTime);
                current.setLeftTime(leftTime);
                current.setCost(cost);
                listOfTickets.add(current);
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        } catch (NoSuchVehicleFoundException ex) {
            ex.printStackTrace();
        } catch (NoSuchCustomerFoundException exception) {
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

        return listOfTickets;


    }

    public List<ParkingTicket> selectAll(String currentStatus) {

        connection = getConnection();
        PreparedStatement preparedStatement = null;
        String query = "SELECT * FROM parking_ticket WHERE status = ?";
        LinkedList<ParkingTicket> listOfTickets = new LinkedList<>();

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,currentStatus);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                //refactor a little
                //you may create separate method that takes resultSet;
                ParkingTicket current = new ParkingTicket();
                Integer id = resultSet.getInt("parking_ticket_id");
                Vehicle vehicle = vehicleDao.select(resultSet.getString("vehicle_id"));
                Customer customer = customerDao.select(resultSet.getInt("customer_id"));
                ParkingSlot parkingSlot = parkingSlotDao.select(resultSet.getInt("parking_slot_id"));
                String status = resultSet.getString("status");
                LocalDateTime arrivalTime = resultSet.getObject("from_time", LocalDateTime.class);
                LocalDateTime leftTime = resultSet.getObject("to_time", LocalDateTime.class);
                BigDecimal cost = resultSet.getBigDecimal("cost");

                current.setParkingTicketID(id);
                current.setVehicle(vehicle);
                current.setCustomer(customer);
                current.setParkingSlot(parkingSlot);
                current.setStatus(status);
                current.setArrivalTime(arrivalTime);
                current.setLeftTime(leftTime);
                current.setCost(cost);
                listOfTickets.add(current);
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        } catch (NoSuchVehicleFoundException ex) {
            ex.printStackTrace();
        } catch (NoSuchCustomerFoundException exception) {
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

        return listOfTickets;

    }
    public List<ParkingTicket> selectInDate(LocalDateTime date) {

        connection = getConnection();
        PreparedStatement preparedStatement = null;
        String query = "SELECT * FROM parking_ticket WHERE from_time >= ?";
        LinkedList<ParkingTicket> listOfTickets = new LinkedList<>();

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setObject(1, date.toLocalDate());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                //refactor a little
                ParkingTicket current = new ParkingTicket();
                Integer id = resultSet.getInt("parking_ticket_id");
                Vehicle vehicle = vehicleDao.select(resultSet.getString("vehicle_id"));
                Customer customer = customerDao.select(resultSet.getInt("customer_id"));
                ParkingSlot parkingSlot = parkingSlotDao.select(resultSet.getInt("parking_slot_id"));
                String status = resultSet.getString("status");
                LocalDateTime arrivalTime = resultSet.getObject("from_time", LocalDateTime.class);
                LocalDateTime leftTime = resultSet.getObject("to_time", LocalDateTime.class);
               BigDecimal cost = resultSet.getBigDecimal("cost");
                current.setParkingTicketID(id);
                current.setVehicle(vehicle);
                current.setCustomer(customer);
                current.setParkingSlot(parkingSlot);
                current.setStatus(status);
                current.setArrivalTime(arrivalTime);
                current.setLeftTime(leftTime);
                current.setCost(cost);
                listOfTickets.add(current);
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        } catch (NoSuchVehicleFoundException ex) {
            ex.printStackTrace();
        } catch (NoSuchCustomerFoundException exception) {
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

        return listOfTickets;
    }

    public List<ParkingTicket> selectInDateAndStatus(LocalDateTime date, String stat) {

        connection = getConnection();
        PreparedStatement preparedStatement = null;
        String query = "SELECT * FROM parking_ticket WHERE from_time >= ? AND status = ?";
        LinkedList<ParkingTicket> listOfTickets = new LinkedList<>();

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setObject(1, date.toLocalDate());
            preparedStatement.setString(2, stat);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                //refactor a little
                ParkingTicket current = new ParkingTicket();
                Integer id = resultSet.getInt("parking_ticket_id");
                Vehicle vehicle = vehicleDao.select(resultSet.getString("vehicle_id"));
                Customer customer = customerDao.select(resultSet.getInt("customer_id"));
                ParkingSlot parkingSlot = parkingSlotDao.select(resultSet.getInt("parking_slot_id"));
                String status = resultSet.getString("status");
                LocalDateTime arrivalTime = resultSet.getObject("from_time", LocalDateTime.class);
                LocalDateTime leftTime = resultSet.getObject("to_time", LocalDateTime.class);
                BigDecimal cost = resultSet.getBigDecimal("cost");
                current.setParkingTicketID(id);
                current.setVehicle(vehicle);
                current.setCustomer(customer);
                current.setParkingSlot(parkingSlot);
                current.setStatus(status);
                current.setArrivalTime(arrivalTime);
                current.setLeftTime(leftTime);
                current.setCost(cost);
                listOfTickets.add(current);
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        } catch (NoSuchVehicleFoundException ex) {
            ex.printStackTrace();
        } catch (NoSuchCustomerFoundException exception) {
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

        return listOfTickets;

    }


    @Override
    public void update(ParkingTicket parkingTicket, Integer id) {

        connection = getConnection();
        PreparedStatement preparedStatement = null;
        String query = "UPDATE parking_ticket SET status = ?,to_time = ?,  cost = ? WHERE parking_ticket_id = ?";

        try {
                    preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setString(1,parkingTicket.getStatus());
                    preparedStatement.setObject(2,parkingTicket.getLeftTime());
                    preparedStatement.setObject(3,parkingTicket.getCost());
                    preparedStatement.setInt(4,id);
                    preparedStatement.execute();
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
    }

    public void updateVehicleID(Integer parkingTicketID, String vehicleID) {

        connection = getConnection();
        PreparedStatement preparedStatement = null;
        String query = "UPDATE parking_ticket SET vehicle_id = ? WHERE parking_ticket_id=?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, vehicleID);
            preparedStatement.setInt(2, parkingTicketID);
            preparedStatement.execute();
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

    }

}
