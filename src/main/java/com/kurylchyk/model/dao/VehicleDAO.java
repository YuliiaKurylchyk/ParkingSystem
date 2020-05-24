package com.kurylchyk.model.dao;

import com.kurylchyk.model.Connector;
import com.kurylchyk.model.Customer;
import com.kurylchyk.model.exceptions.NoSuchVehicleFoundException;
import com.kurylchyk.model.vehicles.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class VehicleDAO extends Connector implements GetUpdateDAO<Vehicle, String>, AddDeleteDAO<Vehicle, String> {

    @Override
    public Vehicle select(String id) throws NoSuchVehicleFoundException {

        String query = "SELECT * FROM vehicle WHERE licence_plate=?";
        ResultSet resultSet = null;
        Vehicle vehicle = null;

        try (Connection connection = Connector.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
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
        }
        throw new NoSuchVehicleFoundException("No Such vehicle found");
    }

    public boolean checkIfPresent(String id) {

        String query = "SELECT status FROM parking_ticket WHERE vehicle_id = ?";
        boolean result = false;
        try (Connection connection = Connector.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
             result = resultSet.getString("status").equals("present");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Vehicle> selectAll() {

        List<Vehicle> allVehicles = new ArrayList<>();
        String query = "SELECT * FROM vehicle";

        try (Connection connection = Connector.getDataSource().getConnection();
             Statement statement = connection.createStatement()) {

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
        String query = "INSERT INTO vehicle(make,model,licence_plate,type) VALUES(?,?,?,?)";

        try (Connection connection = Connector.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, vehicle.getMake());
            preparedStatement.setString(2, vehicle.getModel());
            preparedStatement.setString(3, vehicle.getLicencePlate());
            preparedStatement.setString(4, vehicle.getTypeOfVehicle().toString());
            preparedStatement.execute();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return vehicle.getLicencePlate();
    }

    public void updateCustomerID(Vehicle vehicle, Customer customer) {

        String query = "UPDATE vehicle SET customer_id = ? WHERE licence_plate = ?";

        try (Connection connection = Connector.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, customer.getCustomerID());
            preparedStatement.setString(2, vehicle.getLicencePlate());
            preparedStatement.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public Integer getCountOfType(TypeOfVehicle type) {
        String query = "SELECT COUNT(*) AS COUNT FROM vehicle WHERE type = ?";
        Integer count = 0;
        try (Connection connection = Connector.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, type.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt("COUNT");
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return count;

    }

    @Override
    public void update(Vehicle vehicle, String previousLicencePlate) {

        String query = "UPDATE VEHICLE SET MAKE=?, MODEL=?, LICENCE_PLATE = ?, TYPE = ? WHERE LICENCE_PLATE = ?";

        try (Connection connection = Connector.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, vehicle.getMake());
            preparedStatement.setString(2, vehicle.getModel());
            preparedStatement.setString(3, vehicle.getLicencePlate());
            preparedStatement.setString(4, vehicle.getTypeOfVehicle().toString());
            preparedStatement.setString(5, previousLicencePlate);
            preparedStatement.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void delete(Vehicle vehicle) {

        String query = "DELETE FROM vehicle WHERE licence_plate = ?";

        try (Connection connection = Connector.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, vehicle.getLicencePlate());
            preparedStatement.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}
