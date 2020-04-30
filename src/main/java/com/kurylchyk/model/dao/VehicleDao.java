package com.kurylchyk.model.dao;

import com.kurylchyk.model.Connector;
import com.kurylchyk.model.exceptions.NoSuchVehicleFoundException;
import com.kurylchyk.model.vehicles.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class VehicleDao extends Connector implements GetUpdateDAO<Vehicle, String>,AddDeleteDAO<Vehicle> {

    private Connection connection;


    @Override
    public Vehicle get(String id) throws NoSuchVehicleFoundException {

        connection = getConnection();
        PreparedStatement preparedStatement = null;
        String query = "SELECT * FROM vehicle WHERE licence_plate=?";
        ResultSet resultSet = null;
        Vehicle vehicle = null;

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();


            if (resultSet != null && resultSet.next()) {
                TypeOfVehicle type = TypeOfVehicle.valueOf(resultSet.getString("type"));
                Vehicle vehicleToBeReturned = determineVehicle(type);
                vehicleToBeReturned.setMake(resultSet.getString("make"));
                vehicleToBeReturned.setModel(resultSet.getString("model"));
                vehicleToBeReturned.setLicencePlate(resultSet.getString("licence_plate"));
                vehicleToBeReturned.setType(type);

                return vehicleToBeReturned;
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

        throw new NoSuchVehicleFoundException("No Such vehicle found");
    }

    @Override
    public List<Vehicle> getAll() {
        connection = getConnection();
        List<Vehicle> allVehicles = new ArrayList<>();
        String query = "SELECT * FROM vehicle";
        Statement statement = null;

        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            Vehicle vehicleToBeAdded = null;

            while (resultSet.next()) {

                TypeOfVehicle typeOfCurrentVehicle = TypeOfVehicle.valueOf(resultSet.getString("type"));
                vehicleToBeAdded = determineVehicle(typeOfCurrentVehicle);
                vehicleToBeAdded.setMake(resultSet.getString("make"));
                vehicleToBeAdded.setModel(resultSet.getString("model"));
                vehicleToBeAdded.setLicencePlate(resultSet.getString("licence_plate"));
                vehicleToBeAdded.setType(typeOfCurrentVehicle);

                allVehicles.add(vehicleToBeAdded);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
        return allVehicles;
    }

    private Vehicle determineVehicle(TypeOfVehicle typeOfVehicle) {
        switch (typeOfVehicle) {
            case MOTORBIKE:
                return new Motorbike();
            case CAR:
                return new Car();
            case BUS:
                return new Bus();
            case TRUCK:
                return new Truck();
        }

        return null;
        //change it
    }

    @Override
    public void add(Vehicle vehicle) {

        connection = getConnection();
        PreparedStatement preparedStatement = null;
        String query = "INSERT INTO vehicle(make,model,licence_plate,type,customer_id) VALUES(?,?,?,?,?)";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, vehicle.getMake());
            preparedStatement.setString(2, vehicle.getModel());
            preparedStatement.setString(3, vehicle.getLicencePlate());
            preparedStatement.setString(4, String.valueOf(vehicle.getTypeOfVehicle()));
            preparedStatement.setInt(5,1);
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

    //@Override
    public void update(Vehicle vehicle, String param) {

    }

    //@Override
    public void delete(Vehicle vehicle) {

    }

}
