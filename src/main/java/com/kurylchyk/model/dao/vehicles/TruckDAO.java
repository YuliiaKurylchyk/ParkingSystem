package com.kurylchyk.model.dao.vehicles;

import com.kurylchyk.model.Connector;
import com.kurylchyk.model.parkingTicket.Status;
import com.kurylchyk.model.vehicles.Truck;
import com.kurylchyk.model.vehicles.VehicleType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TruckDAO extends VehicleDAO<Truck, String> {
    @Override
    public String insert(Truck truck) {

        String insertTruck = prop.getProperty("insertTruck");

        super.insert(truck);
        try (Connection connection = Connector.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertTruck)) {
            preparedStatement.setString(1, truck.getLicensePlate());
            preparedStatement.setBoolean(2, truck.getTrailerPresent());
            preparedStatement.execute();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return truck.getLicensePlate();

    }

    @Override
    public void delete(Truck truck) {

        String deleteTruck = prop.getProperty("deleteTruck");

        try (Connection connection = Connector.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteTruck)) {
            preparedStatement.setString(1, truck.getLicensePlate());
            preparedStatement.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        super.delete(truck);
    }

    @Override
    public Optional<Truck> select(String licensePlate)  {
        String selectTruck = prop.getProperty("selectTruck");
        Truck truck = null;
        try (Connection connection = Connector.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectTruck)) {
            preparedStatement.setString(1,licensePlate);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                truck = getVehicle(resultSet);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return Optional.ofNullable(truck);
    }

    @Override
    public List<Truck> selectAll() {
        List<Truck> allVehicles = new ArrayList<>();
        String query = prop.getProperty("selectAllTrucks");

        try (Connection connection = Connector.getDataSource().getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            Truck vehicleToBeAdded = null;

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
    protected VehicleType defineTypeOfVehicle() {
        return VehicleType.TRUCK;
    }

    @Override
    public void update(Truck truck, String licensePlate) {

        String query = prop.getProperty("updateTruck");
        try (Connection connection = Connector.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, truck.getLicensePlate());
            preparedStatement.setBoolean(2, truck.getTrailerPresent());
            preparedStatement.setString(3, licensePlate);
            preparedStatement.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        super.update(truck,licensePlate);
    }

    public List<Truck> selectAll(Status status){
        String query = prop.getProperty("selectAllTrucksByStatus");

        List<Truck> allVehicles = new ArrayList<>();
        try (Connection connection = Connector.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, status.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Truck truck= getVehicle(resultSet);
                allVehicles.add(truck);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return allVehicles;
    }

    protected Truck getVehicle(ResultSet resultSet) throws SQLException {

        //VehicleType typeOfVehicle = VehicleType.valueOf(resultSet.getString("type"));
        String make = resultSet.getString("make");
        String model = resultSet.getString("model");
        String licensePlate = resultSet.getString("license_plate");
        Boolean trailerPresent = resultSet.getBoolean("trailer_present");

        Truck truck = new Truck(make,model,licensePlate,trailerPresent);
        return truck;
    }
}
