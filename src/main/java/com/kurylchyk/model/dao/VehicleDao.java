package com.kurylchyk.model.dao;

import com.kurylchyk.model.Connector;
import com.kurylchyk.model.Customer;
import com.kurylchyk.model.exceptions.NoSuchVehicleFoundException;
import com.kurylchyk.model.vehicles.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class VehicleDao extends Connector implements GetUpdateDAO<Vehicle, String>, AddDeleteDAO<Vehicle, String> {

    private Connection connection;


    @Override
    public Vehicle select(String id) throws NoSuchVehicleFoundException {

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
                vehicleToBeReturned.setTypeOfVehicle(type);

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
    public List<Vehicle> selectAll() {
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
                vehicleToBeAdded.setTypeOfVehicle(typeOfCurrentVehicle);

                allVehicles.add(vehicleToBeAdded);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
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
    public String insert(Vehicle vehicle) {

        connection = getConnection();
        PreparedStatement preparedStatement = null;
        String query = "INSERT INTO vehicle(make,model,licence_plate,type) VALUES(?,?,?,?)";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, vehicle.getMake());
            preparedStatement.setString(2, vehicle.getModel());
            preparedStatement.setString(3, vehicle.getLicencePlate());
            preparedStatement.setString(4, vehicle.getTypeOfVehicle().toString());
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

        return vehicle.getLicencePlate();
    }

    public void updateCustomerID(Vehicle vehicle, Customer customer) {

        PreparedStatement preparedStatement = null;
        connection = getConnection();
        String query = "UPDATE vehicle SET customer_id = ? WHERE licence_plate = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, customer.getCustomerID());
            preparedStatement.setString(2, vehicle.getLicencePlate());
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

    public Integer getCountOfType(TypeOfVehicle type) {

        connection = getConnection();
        PreparedStatement preparedStatement = null;
        Integer count = 0;
        try {
            preparedStatement = connection.prepareStatement("SELECT COUNT(*) AS COUNT FROM vehicle WHERE type = ?");
            preparedStatement.setString(1, type.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt("COUNT");
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
        return count;

    }

    @Override
    public void update(Vehicle vehicle,String previousLicencePlate) {

        connection = getConnection();
        PreparedStatement preparedStatement = null;
        String query = "UPDATE VEHICLE SET MAKE=?, MODEL=?, LICENCE_PLATE = ?, TYPE = ? WHERE LICENCE_PLATE = ?";

        try{
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,vehicle.getMake());
            preparedStatement.setString(2,vehicle.getModel());
            preparedStatement.setString(3,vehicle.getLicencePlate());
            preparedStatement.setString(4,vehicle.getTypeOfVehicle().toString());
            preparedStatement.setString(5,previousLicencePlate);
            preparedStatement.execute();
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
    }

    //@Override
    public void delete(Vehicle vehicle) {

    }


}
