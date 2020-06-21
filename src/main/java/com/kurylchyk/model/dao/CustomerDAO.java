package com.kurylchyk.model.dao;

import com.kurylchyk.model.Connector;
import com.kurylchyk.model.customer.Customer;
import com.kurylchyk.model.exceptions.NoSuchCustomerFoundException;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerDAO extends Connector implements GetUpdateDAO<Customer, Integer>, AddDeleteDAO<Customer, Integer> {



    @Override
    public Optional<Customer> select(Integer id)  {

        String query = "SELECT * FROM customer WHERE customer_id =?";
        Customer customer = null;

        try (Connection connection = Connector.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
               customer = getCustomer(resultSet);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return Optional.ofNullable(customer);
    }

    public Optional<Customer> select(String phoneNumber) {

        String query = "SELECT * FROM customer WHERE phone_number = ?";
        Customer customer = null;

        try (Connection connection = Connector.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, phoneNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                customer = getCustomer(resultSet);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return Optional.ofNullable(customer);
    }

    @Override
    public List<Customer> selectAll() {

        List<Customer> allCustomers = new ArrayList<>();
        String query = "SELECT * FROM customer";

        try (Connection connection = Connector.getDataSource().getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);

            Customer currentCustomer = null;
            while (resultSet.next()) {
                currentCustomer = getCustomer(resultSet);
                allCustomers.add(currentCustomer);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return allCustomers;
    }

    @Override
    public Integer insert(Customer customer) {

        String query = "INSERT INTO customer(name, surname,phone_number) VALUES(?,?,?)";
        Integer id = null;
        try (Connection connection = Connector.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getSurname());
            preparedStatement.setString(3, customer.getPhoneNumber());
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return id;
    }


    @Override
    public void update(Customer customer, Integer id) {

        String query = "UPDATE CUSTOMER SET NAME=?,SURNAME = ?,phone_number = ? WHERE customer_id = ?";
        try (Connection connection = Connector.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getSurname());
            preparedStatement.setString(3, customer.getPhoneNumber());
            preparedStatement.setInt(4, id);
            preparedStatement.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void delete(Customer customer) {

        String query = "DELETE FROM customer WHERE customer_id = ?";

        try (Connection connection = Connector.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, customer.getCustomerID());
            preparedStatement.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    public Integer countCustomersVehicle(Integer customerID) {
        String query = "SELECT COUNT(*) AS COUNT FROM vehicle WHERE customer_id=?";
        Integer count = null;
        try(Connection connection = Connector.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, customerID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                count = resultSet.getInt("COUNT");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return count;
    }

    private   Customer getCustomer(ResultSet resultSet) throws SQLException {
        Integer customerID = resultSet.getInt("customer_id");
        String name  = resultSet.getString("name");
        String surname = resultSet.getString("surname");
        String phoneNumber = resultSet.getString("phone_number");

        Customer customer = Customer.newCustomer().
                setCustomerID(customerID)
                .setName(name)
                .setSurname(surname)
                .setPhoneNumber(phoneNumber)
                .buildCustomer();

        return customer;
    }


    public boolean isPresent(Integer customerID) {
        return select(customerID).isPresent();
    }
    public boolean isPresent(String phoneNumber){
        return select(phoneNumber).isPresent();
    }


}


