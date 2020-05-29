package com.kurylchyk.model.dao;

import com.kurylchyk.model.Connector;
import com.kurylchyk.model.customer.Customer;
import com.kurylchyk.model.exceptions.NoSuchVehicleFoundException;
import com.kurylchyk.model.vehicles.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class VehicleDAO extends Connector implements GetUpdateDAO<Vehicle, String>, AddDeleteDAO<Vehicle, String> {

    @Override
    public Optional<Vehicle> select(String id)  {

        String query = "SELECT * FROM vehicle WHERE licence_plate=?";
        ResultSet resultSet;
        Vehicle vehicle = null;

        try (Connection connection = Connector.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                vehicle = getVehicle(resultSet);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return Optional.ofNullable(vehicle);

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
                vehicleToBeAdded = getVehicle(resultSet);
                allVehicles.add(vehicleToBeAdded);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return allVehicles;
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

    public void updateCustomerID(String licencePlate, Integer customerID) {

        String query = "UPDATE vehicle SET customer_id = ? WHERE licence_plate = ?";

        try (Connection connection = Connector.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, customerID);
            preparedStatement.setString(2, licencePlate);
            preparedStatement.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public String getStatus(String id) {

        String query = "SELECT status FROM parking_ticket WHERE vehicle_id = ?";
        String status = null;
        try (Connection connection = Connector.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                status = resultSet.getString("status");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return status;
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

    private Vehicle getVehicle(ResultSet resultSet) throws SQLException {

        TypeOfVehicle typeOfVehicle = TypeOfVehicle.valueOf(resultSet.getString("type"));
        String make = resultSet.getString("make");
        String model = resultSet.getString("model");
        String licencePlate = resultSet.getString("licence_plate");

        Vehicle vehicle = Vehicle.newVehicle()
                .setType(typeOfVehicle)
                .setMake(make)
                .setModel(model)
                .setLicencePlate(licencePlate)
                .buildVehicle();
        return vehicle;
    }


}
