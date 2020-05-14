package com.kurylchyk.model.dao;

import com.kurylchyk.model.Connector;
import com.kurylchyk.model.Customer;
import com.kurylchyk.model.exceptions.NoSuchCustomerFoundException;
import com.kurylchyk.model.vehicles.TypeOfVehicle;
import sun.util.calendar.CalendarUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDao extends Connector implements GetUpdateDAO<Customer, Integer>, AddDeleteDAO<Customer, Integer> {
    //WHAT IS BETTER - COMPOSITION OR EXTENDING
    private Connection connection;

    @Override
    public Customer select(Integer id) throws NoSuchCustomerFoundException {
        connection = getConnection();
        PreparedStatement preparedStatement = null;
        String query = "SELECT * FROM customer WHERE customer_id =?";
        Customer customer = null;


        try {
            preparedStatement = connection.prepareStatement(query);
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
        } finally {

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

        return customer;
    }

    @Override
    public List<Customer> selectAll() {
        connection = getConnection();
        List<Customer> allCustomers = new ArrayList<>();
        String query = "SELECT * FROM customer";
        Statement statement = null;

        try {
            statement = connection.createStatement();
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
        } finally {
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
        return allCustomers;
    }

    @Override
    public Integer insert(Customer customer) {

        connection = getConnection();
        PreparedStatement preparedStatement = null;
        String query = "INSERT INTO customer(name, surname,phone_number) VALUES(?,?,?)";
        Integer id = null;
        try {
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
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
        } finally {
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

        return id;
    }

    public Integer selectIdByPhoneNumber(String phoneNumber) throws NoSuchCustomerFoundException {

        connection = getConnection();
        PreparedStatement preparedStatement = null;
        String query = "SELECT customer_id FROM customer where phone_number = ?";
        Integer customerID = 0;

        try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, phoneNumber);
            ResultSet set = preparedStatement.executeQuery();
            if (set.next()) {
                customerID = set.getInt(1);
            } else {
                throw new NoSuchCustomerFoundException("No such customer with phone number " + phoneNumber);
            }

        } catch (SQLException exception) {
            exception.printStackTrace();

        } finally {
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

        return customerID;
    }

    @Override
    public void update(Customer customer, Integer id) {
        connection = getConnection();
        PreparedStatement preparedStatement = null;
        String query = "UPDATE CUSTOMER SET NAME=?,SURNAME = ?,phone_number = ? WHERE customer_id = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getSurname());
            preparedStatement.setString(3, customer.getPhoneNumber());
            preparedStatement.setInt(4, id);
            preparedStatement.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
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
    }

    @Override
    public void delete(Customer customer) {
        connection = getConnection();
        PreparedStatement preparedStatement = null;
        String query = "DELETE FROM customer WHERE customer_id = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, customer.getCustomerID());
            preparedStatement.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
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

    }
}


