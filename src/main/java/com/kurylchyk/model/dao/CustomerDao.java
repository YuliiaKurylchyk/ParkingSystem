package com.kurylchyk.model.dao;

import com.kurylchyk.model.Connector;
import com.kurylchyk.model.Customer;
import com.kurylchyk.model.exceptions.NoSuchCustomerFoundException;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDao extends Connector implements GetUpdateDAO<Customer, Integer>, AddDeleteDAO<Customer, Integer> {

    @Override
    public Customer select(Integer id) throws NoSuchCustomerFoundException {

        String query = "SELECT * FROM customer WHERE customer_id =?";
        Customer customer = null;


        try (Connection connection = Connector.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                customer = new Customer(id, resultSet.getString("name"),
                        resultSet.getString("surname"),
                        resultSet.getString("phone_number"));
            } else {
                throw new NoSuchCustomerFoundException("No customer with id " + id + " is found");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return customer;
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
                currentCustomer = new Customer(resultSet.getString("name"),
                        resultSet.getString("surname"),
                        resultSet.getString("phone_number"));
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

    public Integer selectIdByPhoneNumber(String phoneNumber) throws NoSuchCustomerFoundException {

        String query = "SELECT customer_id FROM customer where phone_number = ?";
        Integer customerID = 0;

        try (Connection connection = Connector.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, phoneNumber);
            ResultSet set = preparedStatement.executeQuery();
            if (set.next()) {
                customerID = set.getInt(1);
            } else {
                throw new NoSuchCustomerFoundException("No such customer with phone number " + phoneNumber);
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return customerID;
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


}


