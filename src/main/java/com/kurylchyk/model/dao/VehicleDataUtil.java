package com.kurylchyk.model.dao;

import com.kurylchyk.model.domain.vehicles.vehicleEnum.VehicleType;
import java.sql.*;
import java.util.Optional;
import java.util.Properties;

import com.kurylchyk.model.domain.parkingTicket.ticketEnum.Status;


public class VehicleDataUtil  {

    private Properties prop;
    private Connector connector = new Connector();

     {
        prop =  PropertyLoader.getPropValues(VehicleDataUtil.class,"queries/vehicleQueries.properties");
    }

    public Optional<VehicleType> getType(String licensePlate){

        String query = prop.getProperty("vehicleType");
        VehicleType vehicleType = null;

        try (Connection connection = connector.getDataSource().getConnection();
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

    public  Boolean isPresent(String licensePlate) {

        Boolean isPresent = false;

        String query = prop.getProperty("vehicleIsPresent");
        try (Connection connection = connector.getDataSource().getConnection();
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

    public  Status getStatus(String licensePlate){
        String query = prop.getProperty("vehicleStatus");
        Status status = null;
        try (Connection connection = connector.getDataSource().getConnection();
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

    public void updateCustomerID(String licensePlate, Integer customerID){

        String query = prop.getProperty("vehicleCustomerID");

        try (Connection connection = connector.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, customerID);
            preparedStatement.setString(2, licensePlate);
            preparedStatement.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public  Integer countAllPresent(){
        String query = prop.getProperty("countAll");
        Integer count = 0;
        try(Connection connection = connector.getDataSource().getConnection();
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
