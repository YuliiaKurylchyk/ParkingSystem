package com.kurylchyk.model.dao.vehicles;

import com.kurylchyk.model.Connector;
import com.kurylchyk.model.dao.DAO;
import com.kurylchyk.model.dao.VehicleDAOFactory;
import com.kurylchyk.model.vehicles.*;
import com.kurylchyk.model.parkingTicket.Status;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public abstract class VehicleDAO<T extends Vehicle, S extends String> extends Connector implements DAO<T, String> {

    @Override
    public Optional<T> select(String licensePlate) {

        String query = "SELECT * FROM vehicle WHERE licence_plate=?";
        ResultSet resultSet;
        Vehicle vehicle = null;
        try (Connection connection = Connector.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, licensePlate);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                vehicle = getVehicle(resultSet);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return Optional.ofNullable((T) vehicle);
    }

    @Override
    public List<T> selectAll() {

        List<T> allVehicles = new ArrayList<>();
        String query = "SELECT * FROM vehicle WHERE TYPE = ?";

        try (Connection connection = Connector.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, defineTypeOfVehicle().toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            T vehicleToBeAdded = null;
            while (resultSet.next()) {
                vehicleToBeAdded = (T) getVehicle(resultSet);
                allVehicles.add(vehicleToBeAdded);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return allVehicles;
    }

    @Override
    public String insert(T t) {

        String query = "INSERT INTO vehicle(make,model,licence_plate,type) VALUES(?,?,?,?)";
        Vehicle vehicle = (Vehicle) t;
        try (Connection connection = Connector.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, vehicle.getMake());
            preparedStatement.setString(2, vehicle.getModel());
            preparedStatement.setString(3, vehicle.getLicensePlate());
            preparedStatement.setString(4, vehicle.getVehicleType().toString());
            preparedStatement.execute();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return vehicle.getLicensePlate();
    }

    @Override
    public void delete(T t) {

        String query = "DELETE FROM vehicle WHERE licence_plate = ?";
        Vehicle vehicle = (Vehicle) t;
        try (Connection connection = Connector.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, vehicle.getLicensePlate());
            preparedStatement.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void update(T t, String licensePlate) {

        String query = "UPDATE VEHICLE SET MAKE=?, MODEL=?, LICENCE_PLATE = ?, TYPE = ? WHERE LICENCE_PLATE = ?";
        Vehicle vehicle = (Vehicle) t;

        try (Connection connection = Connector.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, vehicle.getMake());
            preparedStatement.setString(2, vehicle.getModel());
            preparedStatement.setString(3, vehicle.getLicensePlate());
            preparedStatement.setString(4, vehicle.getVehicleType().toString());
            preparedStatement.setString(5, licensePlate);
            preparedStatement.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public List<T> selectAll(Status status) {
        List<T> allVehicles = new ArrayList<>();

        String query = "SELECT * " +
                "FROM VEHICLE AS V " +
                "JOIN PARKING_TICKET AS P " +
                "ON P.vehicle_id = V.licence_plate " +
                "WHERE P.status =? AND TYPE = ?";

        try (Connection connection = Connector.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, status.toString());
            preparedStatement.setString(2, defineTypeOfVehicle().toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Vehicle vehicle = getVehicle(resultSet);
                allVehicles.add((T)vehicle);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return allVehicles;

    }

    protected abstract Vehicle getVehicle(ResultSet resultSet) throws SQLException;

    public Integer countAllPresent() {
        String query = "SELECT COUNT(status) AS allPresent " +
                "FROM parking_ticket AS p " +
                "JOIN vehicle AS V " +
                "ON p.vehicle_id = v.licence_plate " +
                "WHERE p.status = 'PRESENT' and v.type=? ";
        Integer count = 0;
        try (Connection connection = Connector.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, defineTypeOfVehicle().toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt("allPresent");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return count;
    }

    protected abstract VehicleType defineTypeOfVehicle();

}