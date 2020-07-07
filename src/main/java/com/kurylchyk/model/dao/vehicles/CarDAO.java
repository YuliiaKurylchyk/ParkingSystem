package com.kurylchyk.model.dao.vehicles;

import com.kurylchyk.model.parkingTicket.Status;
import com.kurylchyk.model.vehicles.Car;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.kurylchyk.model.Connector;
import com.kurylchyk.model.vehicles.CarSize;
import com.kurylchyk.model.vehicles.Vehicle;
import com.kurylchyk.model.vehicles.VehicleType;

import java.sql.*;

public class CarDAO extends VehicleDAO<Car, String> {


    @Override
    public String insert(Car car) {

        String insertBus = "INSERT INTO CARS(license_plate,car_size) VALUES (?,?)";

        super.insert(car);
        try (Connection connection = Connector.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertBus)) {
            preparedStatement.setString(1, car.getLicensePlate());
            preparedStatement.setString(2, car.getCarSize().toString());
            preparedStatement.execute();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return car.getLicensePlate();
    }

    @Override
    public void delete(Car car) {

        String deleteBus = "DELETE FROM CARS WHERE license_plate = ?";

        try (Connection connection = Connector.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteBus)) {
            preparedStatement.setString(1, car.getLicensePlate());
            preparedStatement.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        super.delete(car);
    }

    @Override
    public Optional<Car> select(String licensePlate) {


        String selectCar = "SELECT * FROM VEHICLE INNER JOIN CARS ON VEHICLE.LICENCE_PLATE = CARS.LICENSE_PLATE WHERE CARS.LICENSE_PLATE = ?";
        Car car = null;
        try (Connection connection = Connector.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectCar)) {
            preparedStatement.setString(1, licensePlate);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                car = getVehicle(resultSet);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return Optional.ofNullable(car);


    }

    @Override
    public List<Car> selectAll() {
        List<Car> allVehicles = new ArrayList<>();

        String query = "SELECT * FROM VEHICLE INNER JOIN CARS ON VEHICLE.LICENCE_PLATE = CARS.LICENSE_PLATE";

        try (Connection connection = Connector.getDataSource().getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            Car vehicleToBeAdded = null;

            while (resultSet.next()) {
                vehicleToBeAdded = getVehicle(resultSet);
                allVehicles.add(vehicleToBeAdded);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return allVehicles;

    }


    public List<Car> selectAll(Status status) {
        List<Car> allVehicles = new ArrayList<>();

        String query = "SELECT  P.STATUS,V.MAKE,V.MODEL,V.LICENCE_PLATE, C.CAR_SIZE FROM VEHICLE AS V INNER JOIN PARKING_TICKET AS P ON V.LICENCE_PLATE = P.VEHICLE_ID INNER JOIN CARS AS C " +
                " ON V.LICENCE_PLATE = C.LICENSE_PLATE " +
                " WHERE P.STATUS = ?";

        try (Connection connection = Connector.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, status.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Car car = getVehicle(resultSet);
                allVehicles.add(car);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return allVehicles;
    }

    @Override
    protected VehicleType defineTypeOfVehicle() {
        return VehicleType.CAR;
    }

    @Override
    public void update(Car car, String licensePlate) {

        String query = "UPDATE CARS SET  LICENSE_PLATE = ?, CAR_SIZE = ? WHERE LICENSE_PLATE = ?";

        try (Connection connection = Connector.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, car.getLicensePlate());
            preparedStatement.setString(2, car.getCarSize().toString());
            preparedStatement.setString(3, licensePlate);
            preparedStatement.execute();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        super.update(car,licensePlate);
    }


    protected Car getVehicle(ResultSet resultSet) throws SQLException {

        System.out.println("In get car dao");
       // VehicleType typeOfVehicle = VehicleType.valueOf(resultSet.getString("type"));
        String make = resultSet.getString("make");
        String model = resultSet.getString("model");
        String licensePlate = resultSet.getString("licence_plate");
        CarSize carSize = CarSize.valueOf(resultSet.getString("CAR_SIZE"));

        Car car = new Car(make, model, licensePlate, carSize);
        return car;
    }
}
