package com.kurylchyk.model.dao;

import com.kurylchyk.model.Connector;
import com.kurylchyk.model.parkingSlots.*;



import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ParkingSlotDao  extends Connector implements GetUpdateDAO<ParkingSlot,Integer> {

    @Override
    public ParkingSlot select(Integer id)  {
        ParkingSlot parkingSlot = null;
        String query = "SELECT size FROM parking_slot WHERE parking_slot_id = ?";

        try(Connection connection = Connector.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet!=null && resultSet.next()){
                SizeOfSlot sizeOfSlot = SizeOfSlot.valueOf(resultSet.getString("size"));
                parkingSlot = determineSizeOfSlot(sizeOfSlot);
            }

        }catch (SQLException exception) {
            exception.printStackTrace();
        }
        return parkingSlot;
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
                currentParkingSlot = determineSizeOfSlot(size);
                allParkingSlots.add(currentParkingSlot);
            }
        }catch (SQLException exception){
            exception.printStackTrace();
        }
        return  allParkingSlots;
    }

    private ParkingSlot determineSizeOfSlot(SizeOfSlot size) {
        ParkingSlot parkingSlot = null;

        switch (size) {
            case SMALL:
                parkingSlot  = new SmallSlot();
                break;
            case MEDIUM:
                parkingSlot =  new MediumSlot();
                break;
            case LARGE:
               parkingSlot = new LargeSlot();
               break;
        }
       return  parkingSlot;
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

    public void update(SizeOfSlot size, Integer param) {
        ParkingSlot parkingSlot = determineSizeOfSlot(size);
        update(parkingSlot,param);
    }


}
