package com.kurylchyk.model.dao;

import com.kurylchyk.model.Connector;
import com.kurylchyk.model.vehicles.VehicleType;

import java.sql.*;
import java.util.Optional;

import  com.kurylchyk.model.parkingTicket.Status;


public class VehicleDataUtil extends Connector {

    public static Optional<VehicleType> getType(String licensePlate){

        String query = "SELECT type FROM vehicle WHERE licence_plate=?";
        VehicleType vehicleType = null;

        try (Connection connection = Connector.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, licensePlate);
           ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                vehicleType = VehicleType.valueOf(resultSet.getString("type"));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
       return Optional.ofNullable(vehicleType);

    }

    public static Boolean isPresent(String licensePlate) {

        Boolean isPresent = false;

        String query = "SELECT * FROM VEHICLE WHERE LICENCE_PLATE=?";
        try (Connection connection = Connector.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, licensePlate);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                isPresent = true;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return isPresent;



    }

    public static Status getStatus(String licensePlate){
        String query = "SELECT status FROM parking_ticket WHERE vehicle_id = ?";
        Status status = null;
        try (Connection connection = Connector.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, licensePlate);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                status = Status.valueOf(resultSet.getString("status"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return status;
    }

    public static void updateCustomerID(String licensePlate, Integer customerID){

        String query = "UPDATE vehicle SET customer_id = ? WHERE licence_plate = ?";

        try (Connection connection = Connector.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, customerID);
            preparedStatement.setString(2, licensePlate);
            preparedStatement.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public static Integer countAllPresent(){
        String query = "SELECT COUNT(*) AS allPresent " +
                "FROM parking_ticket AS p " +
                "JOIN vehicle AS V " +
                "ON p.vehicle_id = v.licence_plate " +
                "WHERE p.status = 'PRESENT'";
        Integer count = 0;
        try(Connection connection = Connector.getDataSource().getConnection();
            Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            if(resultSet.next()){
                count = resultSet.getInt("allPresent");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return count;
    }


}
