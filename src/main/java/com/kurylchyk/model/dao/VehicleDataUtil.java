package com.kurylchyk.model.dao;

import com.kurylchyk.model.Connector;
import com.kurylchyk.model.vehicles.VehicleType;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Optional;
import java.util.Properties;

import  com.kurylchyk.model.parkingTicket.Status;


public class VehicleDataUtil extends Connector {

    private static Properties prop;

    static {
        prop =  PropertyValues.getPropValues(VehicleDataUtil.class,"queries/vehicleQueries.properties");
    }

    public static Optional<VehicleType> getType(String licensePlate){

        String query = prop.getProperty("vehicleType");
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

        String query = prop.getProperty("vehicleIsPresent");
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
        String query = prop.getProperty("vehicleStatus");
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

        String query = prop.getProperty("vehicleCustomerID");

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
        String query = prop.getProperty("countAll");
        Integer count = 0;
        try(Connection connection = Connector.getDataSource().getConnection();
            Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            if(resultSet.next()){
                count = resultSet.getInt("ALL_PRESENT");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return count;
    }



}
