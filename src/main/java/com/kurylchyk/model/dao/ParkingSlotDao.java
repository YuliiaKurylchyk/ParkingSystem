package com.kurylchyk.model.dao;

import com.kurylchyk.model.Connector;
import com.kurylchyk.model.ParkingTicket;
import com.kurylchyk.model.parkingSlots.*;
import com.sun.glass.ui.Size;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ParkingSlotDao  extends Connector implements GetUpdateDAO<ParkingSlot,Integer> {
    private Connection connection;
    //do something with it!!!!
    @Override
    public ParkingSlot select(Integer id)  {
        ParkingSlot parkingSlot = null;
        connection = getConnection();
        PreparedStatement preparedStatement = null;
        String query = "SELECT size FROM parking_slot WHERE parking_slot_id = ?";

        try{
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet!=null && resultSet.next()){
                SizeOfSlot sizeOfSlot = SizeOfSlot.valueOf(resultSet.getString("size"));
                parkingSlot = determineSizeOfSlot(sizeOfSlot);
            }

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

        return parkingSlot;

    }


    @Override
    public List<ParkingSlot> selectAll() {
        connection = getConnection();
        List<ParkingSlot> allParkingSlots = new ArrayList<>();
        Statement statement = null;
        String query = "SELECT * FROM parking_slot";
        try{
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            ParkingSlot currentParkingSlot;
            while(resultSet.next()) {
               SizeOfSlot size = SizeOfSlot.valueOf(resultSet.getString("size"));
                currentParkingSlot = determineSizeOfSlot(size);
                allParkingSlots.add(currentParkingSlot);
            }
        }catch (SQLException exception){
            exception.printStackTrace();
        }finally {
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
        connection = getConnection();
        PreparedStatement preparedStatement = null;
        String query  = "SELECT quantity FROM parking_slot WHERE size = ?";
        int count = 0;

        try{
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,size.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                count = resultSet.getInt(1);
            }

        }catch (SQLException ex){
            ex.printStackTrace();
        }finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            }catch (SQLException exception){
                exception.printStackTrace();
            }
        }
        return count;
    }
    @Override
    public void update(ParkingSlot parkingSlot, Integer quantity) {
        connection = getConnection();
        PreparedStatement preparedStatement = null;
        String query  = "UPDATE parking_slot SET quantity = ? WHERE size = ?";
        try{
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,quantity);
            preparedStatement.setString(2,parkingSlot.getSizeOfSlot().toString());
            preparedStatement.execute();
        }catch (SQLException ex){
            ex.printStackTrace();
        }finally {

            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            }catch (SQLException exception){
                exception.printStackTrace();
            }
        }

    }


    public Integer selectPrice(SizeOfSlot sizeOfSlot) {
        connection = getConnection();
        Integer price = null;
        PreparedStatement preparedStatement = null;
        String query = "SELECT price FROM parking_slot WHERE size = ?";
        try{
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,sizeOfSlot.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                price = resultSet.getInt("price");
            }
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
            }catch (SQLException exception){
                exception.printStackTrace();
            }
        }
        return price;
    }

    public void update(SizeOfSlot size, Integer param) {
        ParkingSlot parkingSlot = determineSizeOfSlot(size);
        update(parkingSlot,param);
    }


}
