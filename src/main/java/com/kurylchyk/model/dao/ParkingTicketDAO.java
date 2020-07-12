package com.kurylchyk.model.dao;

import com.kurylchyk.model.domain.customer.Customer;
import com.kurylchyk.model.domain.parkingSlots.slotEnum.SlotSize;
import com.kurylchyk.model.domain.parkingTicket.ParkingTicket;
import com.kurylchyk.model.domain.parkingSlots.ParkingSlot;
import com.kurylchyk.model.domain.parkingTicket.ticketEnum.Status;
import com.kurylchyk.model.services.impl.parkingSlotDTO.ParkingSlotDTO;
import com.kurylchyk.model.domain.vehicles.Vehicle;
import com.kurylchyk.model.domain.vehicles.vehicleEnum.VehicleType;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class ParkingTicketDAO extends Connector implements DAO<ParkingTicket, Integer> {

    private CustomerDAO customerDAO;
    private ParkingSlotDAO parkingSlotDao;
    private Properties prop;

    {
        prop = PropertyLoader.getPropValues(ParkingTicketDAO.class,"queries/ticketQueries.properties");
        customerDAO = new CustomerDAO();
        parkingSlotDao = new ParkingSlotDAO();

    }

    @Override
    public Integer insert(ParkingTicket parkingTicket) {

        String query = prop.getProperty("insertTicket");
        Integer id = null;
        try (Connection connection = Connector.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setInt(1, parkingTicket.getCustomer().getCustomerID());
            preparedStatement.setString(2, parkingTicket.getVehicle().getLicensePlate());
            preparedStatement.setInt(3, parkingTicket.getParkingSlot().getParkingSlotID());
            preparedStatement.setString(4, parkingTicket.getParkingSlot().getSizeOfSlot().toString());
            preparedStatement.setString(5, parkingTicket.getStatus().toString());
            preparedStatement.setObject(6, parkingTicket.getArrivalTime());
            preparedStatement.executeUpdate();
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

        String query = prop.getProperty("deleteTicket");

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
        String query = prop.getProperty("selectTicket");
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
        ParkingSlotDTO psi = new ParkingSlotDTO(SlotSize.valueOf(resultSet.getString("parking_slot_size")), resultSet.getInt("parking_slot_id"));
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
        String query = prop.getProperty("selectByVehicleID");
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
        String query = prop.getProperty("selectByCustomerID");
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

    public Optional<ParkingTicket> selectByParkingSlot(ParkingSlotDTO identifier) {

        ParkingTicket parkingTicket = null;
        String query = prop.getProperty("selectBySlot");
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
        String query = prop.getProperty("updateTicket");

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

    public void updateParkingSlotID(ParkingTicket parkingTicket,ParkingSlot parkingSlot){

        String query = prop.getProperty("updateSlotID");

        try (Connection connection = Connector.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, parkingSlot.getParkingSlotID());
            preparedStatement.setString(2, parkingSlot.getSizeOfSlot().toString());
            preparedStatement.setInt(3, parkingTicket.getParkingTicketID());
            preparedStatement.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }


    }

    @Override
    public List<ParkingTicket> selectAll() {

        String query = prop.getProperty("selectAll");
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

    public List<ParkingTicket> selectAll(Status status) {

        String query = prop.getProperty("selectAllByStatus");
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

        String query = prop.getProperty("selectAllInDate");
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

        String query = prop.getProperty("selectInDateAndStatus");
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

