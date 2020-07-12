package com.kurylchyk.model.dao.vehicles;

import com.kurylchyk.model.dao.Connector;
import com.kurylchyk.model.domain.vehicles.Bus;
import com.kurylchyk.model.domain.vehicles.vehicleEnum.VehicleType;
import com.kurylchyk.model.domain.parkingTicket.ticketEnum.Status;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BusDAO extends VehicleDAO<Bus,String> {

    @Override
    public String insert(Bus bus) {


        String insertBus = prop.getProperty("insertBus");

        super.insert(bus);
        try (Connection connection = connector.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertBus)) {
            preparedStatement.setString(1, bus.getLicensePlate());
            preparedStatement.setDouble(2, bus.getCountOfSeats());
            preparedStatement.execute();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return bus.getLicensePlate();
    }

    @Override
    public void delete(Bus bus) {

        String deleteBus = prop.getProperty("deleteBus");

        try (Connection connection = connector.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteBus)) {
            preparedStatement.setString(1, bus.getLicensePlate());
            preparedStatement.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        super.delete(bus);
    }

    @Override
    public Optional<Bus> select(String licensePlate)  {

        String selectBus = prop.getProperty("selectBus");
        Bus bus = null;
        try (Connection connection = connector.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectBus)) {
            preparedStatement.setString(1,licensePlate);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                bus = getVehicle(resultSet);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return Optional.ofNullable(bus);
    }

    @Override
    public List<Bus> selectAll() {
        List<Bus> allVehicles = new ArrayList<>();
        String query = prop.getProperty("selectAllBuses");
        try (Connection connection = connector.getDataSource().getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            Bus vehicleToBeAdded = null;

            while (resultSet.next()) {
                vehicleToBeAdded = getVehicle(resultSet);
                allVehicles.add(vehicleToBeAdded);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return allVehicles;
    }


    public List<Bus> selectAll(Status status){
        String query = prop.getProperty("selectAllBusesByStatus");
        List<Bus> allVehicles = new ArrayList<>();
        try (Connection connection = connector.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, status.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Bus bus = getVehicle(resultSet);
                allVehicles.add(bus);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return allVehicles;
    }
    @Override
    protected VehicleType defineTypeOfVehicle() {
        return VehicleType.BUS;
    }


    @Override
    public void update(Bus bus, String licensePlate) {

        String query = prop.getProperty("updateBus");
        try (Connection connection = connector.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, bus.getLicensePlate());
            preparedStatement.setInt(2, bus.getCountOfSeats());
            preparedStatement.setString(3, licensePlate);
            preparedStatement.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        super.update(bus,licensePlate);
    }

    protected Bus getVehicle(ResultSet resultSet) throws SQLException {

       // VehicleType typeOfVehicle = VehicleType.valueOf(resultSet.getString("type"));
        String make = resultSet.getString("make");
        String model = resultSet.getString("model");
        String licensePlate = resultSet.getString("license_plate");
        Integer countOfSeats = resultSet.getInt("count_of_seats");

        Bus bus = new Bus(make,model,licensePlate,countOfSeats);
        return bus;
    }
}
