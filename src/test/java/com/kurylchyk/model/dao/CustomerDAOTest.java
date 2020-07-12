package com.kurylchyk.model.dao;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.sql.DataSource;

public class CustomerDAOTest {
    @Mock
    DataSource mockDataSource;
    @Mock
    Connection mockConnection;
    @Mock
    PreparedStatement mockPreparedStatement;
    @Mock
    ResultSet mockResultSet;

    @InjectMocks
    CustomerDAO customerDAO;

    @BeforeEach
    public void init(){
        MockitoAnnotations.initMocks(this);

    }

}
