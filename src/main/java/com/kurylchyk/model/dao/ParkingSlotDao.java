package com.kurylchyk.model.dao;

import com.kurylchyk.model.Connector;
import com.kurylchyk.model.ParkingTicket;
import com.kurylchyk.model.parkingSlots.*;
import com.sun.glass.ui.Size;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParkingSlotDao  extends Connector implements GetUpdateDAO<ParkingSlot,String> {
    private Connection connection;
    //do something with it!!!!
    @Override
    public ParkingSlot get(String id) throws Exception {
        ParkingSlot parkingSlot = null;
        connection = getConnection();
        PreparedStatement preparedStatement = null;
        String query = "SELECT size FROM parking_slot WHERE parking_slot_id = ?";

        try{
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,id);

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
    public List<ParkingSlot> getAll() {
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

        switch (size) {
            case SMALL:
                return new SmallSlot();
            case MEDIUM:
                return new MediumSlot();
            case LARGE:
                return new LargeSlot();
        }
        return  null;
    }


    @Override
    public void update(ParkingSlot parkingSlot, String param) {

    }



}
