package com.kurylchyk.model.dao;

import com.kurylchyk.model.domain.parkingTicket.ticketEnum.Status;
import com.kurylchyk.model.domain.vehicles.vehicleEnum.VehicleType;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.sql.DataSource;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class VehicleDataUtilTest {

    @Mock
    Connector connector;
    @Mock
    DataSource mockDataSource;
    @Mock
    Connection mockConnection;
    @Mock
    PreparedStatement mockPreparedStatement;
    @Mock
    Statement mockStatement;
    @Mock
    ResultSet mockResultSet;

    @InjectMocks
    private VehicleDataUtil vehicleDataUtil;


    private String correctLicensePlate = "AA 4320 OK";
    private String wrongLicensePlate = "AA 2432 PO";


    @BeforeEach
    public void init() throws SQLException {
        MockitoAnnotations.initMocks(this);

        when(connector.getDataSource()).thenReturn(mockDataSource);
        when(mockDataSource.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        doNothing().when(mockPreparedStatement).setString(anyInt(), anyString());
        doNothing().when(mockPreparedStatement).setInt(anyInt(), anyInt());
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        when(mockResultSet.next()).thenReturn(true).thenReturn(false);

        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);

    }


    @Test
    @DisplayName("Should return type of vehicle by license plate")
    public void shouldReturnType() throws SQLException {

        VehicleType expectedType = VehicleType.TRUCK;
        when(mockResultSet.getString("type")).thenReturn(expectedType.toString());
        VehicleType selectedType = vehicleDataUtil.getType(correctLicensePlate).get();

        assertAll(
                () -> assertNotNull(selectedType),
                () -> assertEquals(expectedType, selectedType));

    }



    @DisplayName("Test if license plate is present in DB")
    @Nested
    class TestIsPresent {
        @Test
        @DisplayName("Should state that license plate is present in db")
        public void shouldBePresent() throws SQLException {

            doNothing().when(mockPreparedStatement).setString(1, correctLicensePlate);
            Boolean result = vehicleDataUtil.isPresent(correctLicensePlate);
            assertTrue(result);
        }

        @Test
        @DisplayName("Should state that license plate is NOT present in db")
        public void shouldNotBePresent() throws SQLException {

            doNothing().when(mockPreparedStatement).setString(1, wrongLicensePlate);

            when(mockResultSet.next()).thenReturn(false);
            Boolean result = vehicleDataUtil.isPresent(wrongLicensePlate);

            assertFalse(result);
        }
    }

    @Test
    @DisplayName("Should return status of vehicle by license plate")
    public void shouldReturnStatus() throws SQLException {

        Status expectedStatus = Status.PRESENT;
        when(mockResultSet.getString("status")).thenReturn(expectedStatus.toString());
        Status selectedStatus = vehicleDataUtil.getStatus(correctLicensePlate);

        assertAll(
                () -> assertNotNull(selectedStatus),
                () -> assertEquals(expectedStatus, selectedStatus));
    }


    @Test
    @DisplayName("Connecting customer ID to vehicle")
    public void shouldConnect() throws SQLException {
        Integer customerID = 12;
        when(mockPreparedStatement.execute()).thenReturn(true);
        vehicleDataUtil.updateCustomerID(correctLicensePlate,customerID);
    }

    @Test
    @DisplayName("Should count all present")
    public void shouldCountAllPresent() throws SQLException {

        Integer expectedCount = 26;
        when(mockResultSet.getInt("ALL_PRESENT")).thenReturn(expectedCount);
        Integer actualCount = vehicleDataUtil.countAllPresent();
        assertEquals(expectedCount,actualCount);

    }

    @AfterEach
    public void verifyCalls() throws SQLException {

        verify(connector).getDataSource();
        verify(mockDataSource).getConnection();

    }


}
