package com.kurylchyk.model.dao;

import com.kurylchyk.model.Connector;
import com.kurylchyk.model.parkingSlots.*;



import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class ParkingSlotDAO extends Connector implements GetUpdateDAO<ParkingSlot,Integer> {

    private ParkingSlotFactory parkingSlotFactory = new ParkingSlotFactory();
    @Override
    public Optional<ParkingSlot> select(Integer id)  {
        ParkingSlot parkingSlot = null;
        String query = "SELECT size FROM parking_slot WHERE parking_slot_id = ?";

        try(Connection connection = Connector.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                SizeOfSlot sizeOfSlot = SizeOfSlot.valueOf(resultSet.getString("size"));
                parkingSlot = parkingSlotFactory.getParkingSlot(sizeOfSlot);
            }

        }catch (SQLException exception) {
            exception.printStackTrace();
        }
        return Optional.ofNullable(parkingSlot);
    }

    @Override
    public List<ParkingSlot> selectAll() {

        List<ParkingSlot> allParkingSlots = new ArrayList<>();
        String query = "SELECT * FROM parking_slot";
        try(Connection connection = Connector.getDataSource().getConnection();
            Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(query);
            ParkingSlot currentParkingSlot;
            while(resultSet.next()) {
               SizeOfSlot size = SizeOfSlot.valueOf(resultSet.getString("size"));
                currentParkingSlot =parkingSlotFactory.getParkingSlot(size);
                allParkingSlots.add(currentParkingSlot);
            }
        }catch (SQLException exception){
            exception.printStackTrace();
        }
        return  allParkingSlots;
    }

    public Integer selectNumberOfSlot(SizeOfSlot size) {

        String query  = "SELECT quantity FROM parking_slot WHERE size = ?";
        int count = 0;

        try(Connection connection = Connector.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1,size.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                count = resultSet.getInt(1);
            }

        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return count;
    }
    @Override
    public void update(ParkingSlot parkingSlot, Integer quantity) {

        String query  = "UPDATE parking_slot SET quantity = ? WHERE size = ?";
        try(Connection connection = Connector.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1,quantity);
            preparedStatement.setString(2,parkingSlot.getSizeOfSlot().toString());
            preparedStatement.execute();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public Integer selectPrice(SizeOfSlot sizeOfSlot) {

        Integer price = null;
        String query = "SELECT price FROM parking_slot WHERE size = ?";
        try(Connection connection = Connector.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1,sizeOfSlot.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                price = resultSet.getInt("price");
            }
        }catch (SQLException exception) {
            exception.printStackTrace();
        }
        return price;
    }



}
