package com.kurylchyk.model.dao;

import com.kurylchyk.model.domain.parkingSlots.*;
import com.kurylchyk.model.domain.parkingSlots.slotEnum.SlotSize;
import com.kurylchyk.model.domain.parkingSlots.slotEnum.SlotStatus;
import com.kurylchyk.model.services.impl.parkingSlotDTOs.ParkingSlotPriceDTO;
import com.kurylchyk.model.services.impl.parkingSlotDTOs.ParkingSlotDTO;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;


public class ParkingSlotDAO extends Connector implements DAO<ParkingSlot, ParkingSlotDTO> {

    private Properties prop;

    {
        prop = PropertyLoader.getPropValues(ParkingSlotDAO.class,"queries/slotQueries.properties");
    }

    @Override
    public ParkingSlotDTO insert(ParkingSlot parkingSlot) {

        String query = prop.getProperty("insertSlot");
        try (Connection connection = Connector.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, parkingSlot.getParkingSlotID());
            preparedStatement.setString(2, parkingSlot.getSizeOfSlot().toString());
            preparedStatement.setString(3, parkingSlot.getStatus().toString());
            preparedStatement.execute();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return new ParkingSlotDTO(parkingSlot.getSizeOfSlot(), parkingSlot.getParkingSlotID());
    }

    @Override
    public void delete(ParkingSlot parkingSlot) {
        String query = prop.getProperty("deleteSlot");
        try (Connection connection = Connector.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, parkingSlot.getParkingSlotID());
            preparedStatement.setString(2, parkingSlot.getSizeOfSlot().toString());
            preparedStatement.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public Optional<ParkingSlot> select(ParkingSlotDTO parkingSlotIdentifier) {

        ParkingSlot parkingSlot = null;

        String query = prop.getProperty("selectSlot");

        try (Connection connection = Connector.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, parkingSlotIdentifier.getParkingSlotID());
            preparedStatement.setString(2, parkingSlotIdentifier.getSlotSize().toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                parkingSlot = getParkingSlot(resultSet);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return Optional.ofNullable(parkingSlot);
    }

    @Override
    public List<ParkingSlot> selectAll() {

        List<ParkingSlot> allParkingSlots = new ArrayList<>();
        String query = prop.getProperty("selectAll");
        try (Connection connection = Connector.getDataSource().getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            ParkingSlot currentParkingSlot;
            while (resultSet.next()) {
                currentParkingSlot = getParkingSlot(resultSet);
                allParkingSlots.add(currentParkingSlot);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return allParkingSlots;
    }

    @Override
    public void update(ParkingSlot parkingSlot, ParkingSlotDTO parkingSlotIdentifier) {

        String query = prop.getProperty("updateSlot");

        try (Connection connection = Connector.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, parkingSlot.getStatus().toString());
            preparedStatement.setInt(2, parkingSlotIdentifier.getParkingSlotID());
            preparedStatement.setString(3, parkingSlotIdentifier.getSlotSize().toString());
            preparedStatement.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    /*
    public SlotStatus getSlotStatus(ParkingSlotDTO parkingSlotIdentifier) {

        String query = "SELECT SLOT_STATUS FROM PARKING_SLOTS WHERE PARKING_SLOT_ID = ? AND SIZE=?";
        SlotStatus slotStatus = null;

        try (Connection connection = Connector.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, parkingSlotIdentifier.getParkingSlotID());
            preparedStatement.setString(2, parkingSlotIdentifier.getSlotSize().toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                slotStatus = slotStatus.valueOf(resultSet.getString("SLOT_STATUS"));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return slotStatus;
    }

     */

    public List<ParkingSlot> selectAll(SlotSize slotSize) {
        List<ParkingSlot> allParkingSlots = new ArrayList<>();
        String query = prop.getProperty("selectAllWithSize");
        try (Connection connection = Connector.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, slotSize.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            ParkingSlot currentParkingSlot;
            while (resultSet.next()) {
                currentParkingSlot = getParkingSlot(resultSet);
                allParkingSlots.add(currentParkingSlot);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return allParkingSlots;
    }

    public List<ParkingSlot> selectAvailable(SlotSize slotSize) {
        List<ParkingSlot> allParkingSlots = new ArrayList<>();
        String query = prop.getProperty("selectAvailable");
        try (Connection connection = Connector.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, slotSize.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            ParkingSlot currentParkingSlot;
            while (resultSet.next()) {
                currentParkingSlot = getParkingSlot(resultSet);
                allParkingSlots.add(currentParkingSlot);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return allParkingSlots;
    }

    public Integer getPrice(SlotSize slotSize) {
        String query = prop.getProperty("selectPrice");
        Integer price = null;

        try (Connection connection = Connector.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, slotSize.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                price = resultSet.getInt("price");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return price;
    }


    public void updatePrice(SlotSize slotSize, Integer price) {

        String query = prop.getProperty("updatePrice");

        try (Connection connection = Connector.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, price);
            preparedStatement.setString(2, slotSize.toString());
            preparedStatement.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

/*
    public Integer countOfAvailable(SlotSize slotSize) {
        Integer count = null;

        String query = "SELECT COUNT(*) as COUNT FROM PARKING_SLOTS WHERE slot_status = 'VACANT' AND size = ?";
        try (Connection connection = Connector.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, slotSize.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt("count");
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return count;
    }

 */

    public List<ParkingSlotPriceDTO> getSlotsPrice() {

        List<ParkingSlotPriceDTO> list = new ArrayList<>();
        String query = prop.getProperty("selectSlotsPrice");
        try (Connection connection = Connector.getDataSource().getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                SlotSize slotSize = SlotSize.valueOf(resultSet.getString("size"));
                Integer price = resultSet.getInt("price");
                list.add(new ParkingSlotPriceDTO(slotSize, price));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return list;


    }

    public Integer getLastID(SlotSize slotSize) {

        Integer lastID = null;
        String query = prop.getProperty("selectLastID");

        try (Connection connection = Connector.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1,slotSize.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                lastID = resultSet.getInt("LAST_ID");
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return lastID;
    }

    private ParkingSlot getParkingSlot(ResultSet resultSet) throws SQLException {

        ParkingSlot parkingSlot = null;
        SlotSize sizeOfSlot = SlotSize.valueOf(resultSet.getString("size"));
        SlotStatus slotStatus = SlotStatus.valueOf(resultSet.getString("slot_status"));
        Integer parkingSlotID = resultSet.getInt("parking_slot_id");
        Integer price = getPrice(sizeOfSlot);
        parkingSlot = new ParkingSlot(parkingSlotID, sizeOfSlot, slotStatus, price);
        return parkingSlot;
    }


}
