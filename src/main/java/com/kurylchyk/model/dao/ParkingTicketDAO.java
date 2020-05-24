package com.kurylchyk.model.dao;

import com.kurylchyk.model.Connector;
import com.kurylchyk.model.customer.Customer;
import com.kurylchyk.model.parkingTicket.ParkingTicket;
import com.kurylchyk.model.exceptions.NoSuchCustomerFoundException;
import com.kurylchyk.model.exceptions.NoSuchParkingTicketException;
import com.kurylchyk.model.exceptions.NoSuchVehicleFoundException;
import com.kurylchyk.model.parkingSlots.ParkingSlot;
import com.kurylchyk.model.vehicles.TypeOfVehicle;
import com.kurylchyk.model.vehicles.Vehicle;
import com.sun.security.sasl.ClientFactoryImpl;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ParkingTicketDAO extends Connector implements GetUpdateDAO<ParkingTicket, Integer>, AddDeleteDAO<ParkingTicket, Integer> {

    private CustomerDAO customerDao;
    private VehicleDAO vehicleDao;
    private ParkingSlotDAO parkingSlotDao;

    {
        customerDao = new CustomerDAO();
        vehicleDao = new VehicleDAO();
        parkingSlotDao = new ParkingSlotDAO();

    }


    @Override
    public Integer insert(ParkingTicket parkingTicket) {

        String query = "INSERT INTO parking_ticket(customer_id,vehicle_id,parking_slot_id,status,from_time)" +
                "VALUES (?,?,?,?,?)";
        Integer id = null;
        try(Connection connection = Connector.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS)) {

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
        }

        return id;
    }

    @Override
    public void delete(ParkingTicket parkingTicket) {

        String query = "DELETE FROM parking_ticket WHERE parking_ticket_id = ?";

        try(Connection connection = Connector.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1,parkingTicket.getParkingTicketID());
            preparedStatement.execute();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public ParkingTicket select(Integer id) throws NoSuchParkingTicketException {

        ParkingTicket parkingTicket = null;
        String query = "SELECT * FROM parking_ticket WHERE parking_ticket_id = ?";
        try(Connection connection = Connector.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
              parkingTicket = getParkingTicket(resultSet);
            } else {
                throw new NoSuchParkingTicketException("Parking ticket with id " + id + "is found");
            }
        } catch (SQLException | NoSuchVehicleFoundException | NoSuchCustomerFoundException exception) {
            exception.printStackTrace();
        }
        return parkingTicket;
    }

    protected ParkingTicket getParkingTicket(ResultSet resultSet)
            throws SQLException, NoSuchVehicleFoundException, NoSuchCustomerFoundException {


        Vehicle currentVehicle = vehicleDao.select(resultSet.getString("vehicle_id"));
        Customer currentCustomer = customerDao.select(resultSet.getInt("customer_id"));
        ParkingSlot currentParkingSlot = parkingSlotDao.select(resultSet.getInt("parking_slot_id"));
        Integer parkingTicketID = resultSet.getInt("parking_ticket_id");
        String status  = resultSet.getString("status");
        LocalDateTime arrivalTime = resultSet.getObject("from_time", LocalDateTime.class);
        LocalDateTime leftTime = resultSet.getObject("to_time",LocalDateTime.class);
        BigDecimal cost = resultSet.getBigDecimal("cost");

        ParkingTicket parkingTicket = ParkingTicket.newParkingTicket()
                .withParkingTicketID(parkingTicketID)
                .withVehicle(currentVehicle)
                .withCustomer(currentCustomer)
                .withParkingSlot(currentParkingSlot)
                .withArrivalTime(arrivalTime)
                .withLeftTime(leftTime)
                .withStatus(status)
                .withCost(cost)
                .buildTicket();
        return parkingTicket;
    }

    public ParkingTicket selectByVehicleID(String vehicleID) {

        ParkingTicket parkingTicket = null;
        String query = "SELECT * FROM parking_ticket WHERE vehicle_id = ?";
        try(Connection connection = Connector.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, vehicleID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                parkingTicket = getParkingTicket(resultSet);
            } else {
                throw new NoSuchParkingTicketException("Parking ticket with licence plate " + vehicleID + "is found");
            }
        } catch (SQLException | NoSuchVehicleFoundException | NoSuchCustomerFoundException | NoSuchParkingTicketException exception) {
            exception.printStackTrace();
        }
        return parkingTicket;
    }

    //add exception NoSuchParkingTicketException
    public List <ParkingTicket> selectByCustomerID(Integer customerID) {

        List<ParkingTicket> allTickets = new ArrayList<>();
        String query = "SELECT * FROM parking_ticket WHERE customer_id = ?";
        try(Connection connection = Connector.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, customerID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
               ParkingTicket currentTicket = getParkingTicket(resultSet);
                allTickets.add(currentTicket);
            }
        } catch (SQLException | NoSuchVehicleFoundException | NoSuchCustomerFoundException exception) {
            exception.printStackTrace();
        }

        return allTickets;
    }

    public Integer countCustomer(Customer customer) {
        Integer customerID = customer.getCustomerID();
        String query = "SELECT COUNT(*) AS COUNT FROM vehicle WHERE customer_id=?";
        Integer count = null;
        try(Connection connection = Connector.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, customerID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                count = resultSet.getInt("COUNT");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return count;
    }
    public Integer countAllPresent(TypeOfVehicle typeOfVehicle){

        String query = "SELECT COUNT(status) AS allPresent " +
                        "FROM parking_ticket AS p " +
                        "JOIN vehicle AS V " +
                        "ON p.vehicle_id = v.licence_plate " +
                        "WHERE p.status = 'present' and v.type=? ";
        Integer count = 0;
        try(Connection connection = Connector.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1,typeOfVehicle.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                count = resultSet.getInt("allPresent");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return count;
    }

    @Override
    public List<ParkingTicket> selectAll() {

        String query = "SELECT * FROM parking_ticket";
        LinkedList<ParkingTicket> listOfTickets = new LinkedList<>();

        try(Connection connection = Connector.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
           ParkingTicket currentTicket = getParkingTicket(resultSet);
           listOfTickets.add(currentTicket);
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        } catch (NoSuchVehicleFoundException ex) {
            ex.printStackTrace();
        } catch (NoSuchCustomerFoundException exception) {
            exception.printStackTrace();
        }

        return listOfTickets;


    }

    public List<ParkingTicket> selectAll(String currentStatus) {

        String query = "SELECT * FROM parking_ticket WHERE status = ?";
        LinkedList<ParkingTicket> listOfTickets = new LinkedList<>();

        try(Connection connection = Connector.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1,currentStatus);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                ParkingTicket currentTicket = getParkingTicket(resultSet);
                listOfTickets.add(currentTicket);
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        } catch (NoSuchVehicleFoundException ex) {
            ex.printStackTrace();
        } catch (NoSuchCustomerFoundException exception) {
            exception.printStackTrace();
        }
        return listOfTickets;

    }
    public List<ParkingTicket> selectInDate(LocalDateTime date) {

        String query = "SELECT * FROM parking_ticket WHERE from_time >= ?";
        LinkedList<ParkingTicket> listOfTickets = new LinkedList<>();

        try(Connection connection = Connector.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setObject(1, date.toLocalDate());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                ParkingTicket currentTicket = getParkingTicket(resultSet);
                listOfTickets.add(currentTicket);
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        } catch (NoSuchVehicleFoundException ex) {
            ex.printStackTrace();
        } catch (NoSuchCustomerFoundException exception) {
            exception.printStackTrace();
        }
        return listOfTickets;
    }

    public List<ParkingTicket> selectInDateAndStatus(LocalDateTime date, String stat) {

        String query = "SELECT * FROM parking_ticket WHERE from_time >= ? AND status = ?";
        LinkedList<ParkingTicket> listOfTickets = new LinkedList<>();

        try(Connection connection = Connector.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setObject(1, date.toLocalDate());
            preparedStatement.setString(2, stat);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ParkingTicket currentTicket = getParkingTicket(resultSet);
                listOfTickets.add(currentTicket);
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        } catch (NoSuchVehicleFoundException ex) {
            ex.printStackTrace();
        } catch (NoSuchCustomerFoundException exception) {
            exception.printStackTrace();
        }

        return listOfTickets;
    }

    @Override
    public void update(ParkingTicket parkingTicket, Integer id) {
        String query = "UPDATE parking_ticket SET status = ?,to_time = ?,  cost = ? WHERE parking_ticket_id = ?";

        try(Connection connection = Connector.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1,parkingTicket.getStatus());
                    preparedStatement.setObject(2,parkingTicket.getLeftTime());
                    preparedStatement.setObject(3,parkingTicket.getCost());
                    preparedStatement.setInt(4,id);
                    preparedStatement.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void updateVehicleID(Integer parkingTicketID, String vehicleID) {

        String query = "UPDATE parking_ticket SET vehicle_id = ? WHERE parking_ticket_id=?";

        try(Connection connection = Connector.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, vehicleID);
            preparedStatement.setInt(2, parkingTicketID);
            preparedStatement.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

    }




}

