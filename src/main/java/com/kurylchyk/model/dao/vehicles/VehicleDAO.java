package com.kurylchyk.model.dao.vehicles;

import com.kurylchyk.model.dao.Connector;
import com.kurylchyk.model.dao.DAO;
import com.kurylchyk.model.dao.PropertyLoader;
import com.kurylchyk.model.domain.vehicles.*;
import com.kurylchyk.model.domain.parkingTicket.ticketEnum.Status;
import com.kurylchyk.model.domain.vehicles.vehicleEnum.VehicleType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;


public abstract class VehicleDAO<T extends Vehicle, S extends String> extends Connector implements DAO<T, String> {

    protected Properties prop;

    {
        prop = PropertyLoader.getPropValues(VehicleDAO.class,"queries/vehicleQueries.properties");
    }

    @Override
    public Optional<T> select(String licensePlate) {



        String query = prop.getProperty("selectVehicle");
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
        String query = prop.getProperty("selectAll");
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

        String query = prop.getProperty("insertVehicle");
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

        String query = prop.getProperty("deleteVehicle");
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

        String query = prop.getProperty("updateVehicle");

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

        String query = prop.getProperty("selectAllByStatus");
        try (Connection connection = Connector.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, status.toString());
            preparedStatement.setString(2, defineTypeOfVehicle().toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Vehicle vehicle = getVehicle(resultSet);
                allVehicles.add((T) vehicle);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return allVehicles;

    }

    protected abstract Vehicle getVehicle(ResultSet resultSet) throws SQLException;

    public Integer countAllPresent() {
        String query = prop.getProperty("countAllPresent");
        Integer count = 0;
        try (Connection connection = Connector.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, defineTypeOfVehicle().toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt("ALL_PRESENT");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return count;
    }

    protected abstract VehicleType defineTypeOfVehicle();


}
