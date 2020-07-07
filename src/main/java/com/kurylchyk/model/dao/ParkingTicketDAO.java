package com.kurylchyk.model.dao;

import com.kurylchyk.model.Connector;
import com.kurylchyk.model.customer.Customer;
import com.kurylchyk.model.dao.vehicles.VehicleDAO;
import com.kurylchyk.model.parkingSlots.SlotSize;
import com.kurylchyk.model.parkingTicket.ParkingTicket;
import com.kurylchyk.model.parkingSlots.ParkingSlot;
import com.kurylchyk.model.parkingTicket.Status;
import com.kurylchyk.model.vehicles.Vehicle;
import com.kurylchyk.model.vehicles.VehicleType;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class ParkingTicketDAO extends Connector implements DAO<ParkingTicket, Integer> {

    private CustomerDAO customerDAO;
    private VehicleDAO vehicleDAO;
    private ParkingSlotDAO parkingSlotDao;

    {

        customerDAO = new CustomerDAO();
        parkingSlotDao = new ParkingSlotDAO();

    }

    @Override
    public Integer insert(ParkingTicket parkingTicket) {

        String query = "INSERT INTO parking_ticket(customer_id,vehicle_id,parking_slot_id,parking_slot_size,status,from_time)" +
                "VALUES (?,?,?,?,?,?)";
        Integer id = null;
        try (Connection connection = Connector.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setInt(1, parkingTicket.getCustomer().getCustomerID());
            preparedStatement.setString(2, parkingTicket.getVehicle().getLicensePlate());
            preparedStatement.setInt(3, parkingTicket.getParkingSlot().getParkingSlotID());
            preparedStatement.setString(4, parkingTicket.getParkingSlot().getSizeOfSlot().toString());
            preparedStatement.setString(5, parkingTicket.getStatus().toString());
            preparedStatement.setObject(6, parkingTicket.getArrivalTime());
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return id;
    }

    @Override
    public void delete(ParkingTicket parkingTicket) {

        String query = "DELETE FROM parking_ticket WHERE parking_ticket_id = ?";

        try (Connection connection = Connector.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, parkingTicket.getParkingTicketID());
            preparedStatement.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Optional<ParkingTicket> select(Integer parkingSlotID) {

        ParkingTicket parkingTicket = null;
        String query = "SELECT * FROM parking_ticket WHERE parking_ticket_id = ?";
        try (Connection connection = Connector.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, parkingSlotID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                parkingTicket = getParkingTicket(resultSet);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return Optional.ofNullable(parkingTicket);
    }

    private ParkingTicket getParkingTicket(ResultSet resultSet) throws SQLException {


        VehicleType vehicleType = VehicleDataUtil.getType(resultSet.getString("vehicle_id")).get();
        Vehicle currentVehicle = (Vehicle) VehicleDAOFactory.getVehicleDAO(vehicleType).select(resultSet.getString("vehicle_id")).get();
        Customer currentCustomer = customerDAO.select(resultSet.getInt("customer_id")).get();
        ParkingSlotIdentifier psi = new ParkingSlotIdentifier(SlotSize.valueOf(resultSet.getString("parking_slot_size")), resultSet.getInt("parking_slot_id"));
        ParkingSlot currentParkingSlot = parkingSlotDao.select(psi).get();
        Integer parkingTicketID = resultSet.getInt("parking_ticket_id");
        Status status = Status.valueOf(resultSet.getString("status").toUpperCase());
        LocalDateTime arrivalTime = resultSet.getObject("from_time", LocalDateTime.class);
        LocalDateTime leftTime = resultSet.getObject("to_time", LocalDateTime.class);
        BigDecimal cost = resultSet.getBigDecimal("cost");

        ParkingTicket parkingTicket = ParkingTicket.newParkingTicket()
                .withParkingTicketID(parkingTicketID)
                .withVehicle(currentVehicle)
                .withCustomer(currentCustomer)
                .withParkingSlot(currentParkingSlot)
                .withArrivalTime(arrivalTime)
                .withDepartureTime(leftTime)
                .withStatus(status)
                .withCost(cost)
                .buildTicket();
        return parkingTicket;
    }


    public Optional<ParkingTicket> selectByVehicleID(String vehicleID) {

        ParkingTicket parkingTicket = null;
        String query = "SELECT * FROM parking_ticket WHERE vehicle_id = ?";
        try (Connection connection = Connector.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, vehicleID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                parkingTicket = getParkingTicket(resultSet);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return Optional.ofNullable(parkingTicket);
    }

    public List<ParkingTicket> selectByCustomerID(Integer customerID) {

        List<ParkingTicket> allTickets = new ArrayList<>();
        String query = "SELECT * FROM parking_ticket WHERE customer_id = ?";
        try (Connection connection = Connector.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, customerID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ParkingTicket currentTicket = getParkingTicket(resultSet);
                allTickets.add(currentTicket);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return allTickets;
    }

    public Optional<ParkingTicket> selectByParkingSlot(ParkingSlotIdentifier identifier) {

        ParkingTicket parkingTicket = null;
        String query = "SELECT * FROM parking_ticket WHERE parking_slot_id = ? AND parking_slot_size = ?";
        try (Connection connection = Connector.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, identifier.getParkingSlotID());
            preparedStatement.setString(2, identifier.getSlotSize().toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                parkingTicket = getParkingTicket(resultSet);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return Optional.ofNullable(parkingTicket);
    }

    @Override
    public void update(ParkingTicket parkingTicket, Integer id) {
        String query = "UPDATE parking_ticket SET status = ?,to_time = ?,  cost = ? WHERE parking_ticket_id = ?";

        try (Connection connection = Connector.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, parkingTicket.getStatus().toString());
            preparedStatement.setObject(2, parkingTicket.getDepartureTime());
            preparedStatement.setObject(3, parkingTicket.getCost());
            preparedStatement.setInt(4, id);
            preparedStatement.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void updateVehicleID(Integer parkingTicketID, String vehicleID) {

        String query = "UPDATE parking_ticket SET vehicle_id = ? WHERE parking_ticket_id=?";

        try (Connection connection = Connector.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, vehicleID);
            preparedStatement.setInt(2, parkingTicketID);
            preparedStatement.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

    }

    @Override
    public List<ParkingTicket> selectAll() {

        String query = "SELECT * FROM parking_ticket";
        LinkedList<ParkingTicket> listOfTickets = new LinkedList<>();

        try (Connection connection = Connector.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                ParkingTicket currentTicket = getParkingTicket(resultSet);
                listOfTickets.add(currentTicket);
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return listOfTickets;


    }

    public boolean isPresent(Integer parkingTicketID) {
        return select(parkingTicketID).isPresent();
    }

    public List<ParkingTicket> selectAll(Status status) {

        String query = "SELECT * FROM parking_ticket WHERE status = ?";
        LinkedList<ParkingTicket> listOfTickets = new LinkedList<>();

        try (Connection connection = Connector.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, status.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                ParkingTicket currentTicket = getParkingTicket(resultSet);
                listOfTickets.add(currentTicket);
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return listOfTickets;

    }

    public List<ParkingTicket> selectInDate(LocalDateTime date) {

        String query = "SELECT * FROM parking_ticket WHERE from_time >= ?";
        LinkedList<ParkingTicket> listOfTickets = new LinkedList<>();

        try (Connection connection = Connector.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setObject(1, date.toLocalDate());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                ParkingTicket currentTicket = getParkingTicket(resultSet);
                listOfTickets.add(currentTicket);
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return listOfTickets;
    }

    public List<ParkingTicket> selectInDateAndStatus(LocalDateTime date, Status status) {

        String query = "SELECT * FROM parking_ticket WHERE from_time >= ? AND status = ?";
        LinkedList<ParkingTicket> listOfTickets = new LinkedList<>();

        try (Connection connection = Connector.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setObject(1, date.toLocalDate());
            preparedStatement.setString(2, status.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ParkingTicket currentTicket = getParkingTicket(resultSet);
                listOfTickets.add(currentTicket);
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return listOfTickets;
    }


}

