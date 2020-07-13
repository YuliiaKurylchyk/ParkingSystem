package com.kurylchyk.model.dao;

import com.kurylchyk.model.dao.vehicles.MotorbikeDAO;
import com.kurylchyk.model.dao.vehicles.VehicleDAO;
import com.kurylchyk.model.domain.customer.Customer;
import com.kurylchyk.model.domain.parkingSlots.ParkingSlot;
import com.kurylchyk.model.domain.parkingSlots.slotEnum.SlotSize;
import com.kurylchyk.model.domain.parkingSlots.slotEnum.SlotStatus;
import com.kurylchyk.model.domain.parkingTicket.ParkingTicket;
import com.kurylchyk.model.domain.parkingTicket.ticketEnum.Status;
import com.kurylchyk.model.domain.vehicles.Motorbike;
import com.kurylchyk.model.domain.vehicles.vehicleEnum.VehicleType;
import com.kurylchyk.model.services.impl.parkingSlotDTO.ParkingSlotDTO;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;


@DisplayName("Test of some parking ticket DAO methods")
public class ParkingTicketDAOTest {

    @Mock
    Connector connector;
    @Mock
    DataSource mockDataSource;
    @Mock
    Connection mockConnection;
    @Mock
    PreparedStatement mockPreparedStatement;
    @Mock
    ResultSet mockResultSet;
    @Mock
    CustomerDAO customerDAO;
    @Mock
    VehicleDataUtil vehicleDataUtil;
    @Mock
    ParkingSlotDAO parkingSlotDAO;
    @Mock
    VehicleDAO vehicleDAO;

    @InjectMocks
    ParkingTicketDAO parkingTicketDAO;


    private static Integer expectedID ;
    private static Motorbike expectedVehicle;
    private static Customer expectedCustomer;
    private static ParkingSlot expectedParkingSlot;
    private static Status expectedStatus;
    private static LocalDateTime expectedArrivalTime;
    private static LocalDateTime expectedDepartureTime;
    private static BigDecimal expectedCost;

    private static ParkingTicket parkingTicketPresent;
    private static ParkingTicket parkingTicketLeft;

   @BeforeAll
    public static void initAll() {
        expectedID = 1;
        expectedVehicle =  new Motorbike("Harley Davidson","Iron 883","AA 2902 KL");
        expectedCustomer  = Customer.newCustomer().setCustomerID(1)
                .setName("Name").setSurname("Surname")
                .setPhoneNumber("+380961095829").buildCustomer();
        expectedParkingSlot = new ParkingSlot(1, SlotSize.SMALL,SlotStatus.OCCUPIED);
        expectedStatus = Status.PRESENT;

        expectedArrivalTime = LocalDateTime.now().minusDays(3);
       expectedDepartureTime = LocalDateTime.now();
       expectedCost = new BigDecimal(45);

       parkingTicketPresent = ParkingTicket.newParkingTicket().withParkingTicketID(expectedID)
               .withParkingSlot(expectedParkingSlot).withStatus(expectedStatus).withCustomer(expectedCustomer).withVehicle(expectedVehicle)
               .withArrivalTime(expectedArrivalTime).buildTicket();

       parkingTicketLeft = ParkingTicket.newParkingTicket().withParkingTicketID(expectedID)
               .withParkingSlot(expectedParkingSlot).withStatus(expectedStatus).withCustomer(expectedCustomer).withVehicle(expectedVehicle)
               .withArrivalTime(expectedArrivalTime).withDepartureTime(expectedDepartureTime).withCost(expectedCost).buildTicket();
   }

    @BeforeEach
    public void init() throws SQLException {
        MockitoAnnotations.initMocks(this);

        when(connector.getDataSource()).thenReturn(mockDataSource);
        when(mockDataSource.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        doNothing().when(mockPreparedStatement).setString(anyInt(), anyString());
        doNothing().when(mockPreparedStatement).setInt(anyInt(), anyInt());
        doNothing().when(mockPreparedStatement).setObject(anyInt(), anyObject());

        when(mockResultSet.next()).thenReturn(true).thenReturn(false);

        when(vehicleDataUtil.getType(expectedVehicle.getLicensePlate())).thenReturn(Optional.ofNullable(VehicleType.MOTORBIKE));

        when(vehicleDAO.select(expectedVehicle.getLicensePlate())).thenReturn(Optional.ofNullable(expectedVehicle));

        when(customerDAO.select(expectedCustomer.getCustomerID())).thenReturn(Optional.ofNullable(expectedCustomer));

        when(parkingSlotDAO.select(any(ParkingSlotDTO.class))).thenReturn(Optional.ofNullable(expectedParkingSlot));

        when(mockResultSet.getString("parking_slot_size")).thenReturn(expectedParkingSlot.getSizeOfSlot().toString());
        when(mockResultSet.getInt("parking_slot_id")).thenReturn(expectedParkingSlot.getParkingSlotID());
        when(mockResultSet.getString("vehicle_id")).thenReturn(expectedVehicle.getLicensePlate());
        when(mockResultSet.getInt("customer_id")).thenReturn(expectedCustomer.getCustomerID());
        when(mockResultSet.getString("status")).thenReturn(String.valueOf(expectedStatus));
        when(mockResultSet.getObject("from_time")).thenReturn(expectedArrivalTime);
        when(mockResultSet.getObject("to_time")).thenReturn(expectedDepartureTime);
        when(mockResultSet.getBigDecimal("cost")).thenReturn(expectedCost);
    }


    @Test
    @DisplayName("Should insert  parking ticket to database")
    public void shouldInsertToDatabase() throws SQLException {

        when(mockConnection.prepareStatement(anyString(),eq(1))).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.getGeneratedKeys()).thenReturn(mockResultSet);

        when(mockResultSet.getInt(1)).thenReturn(expectedID);

        Integer generatedID=  parkingTicketDAO.insert(parkingTicketPresent);
        assertAll(
                ()->assertNotNull(generatedID),
                ()->  assertEquals(generatedID,expectedID));

        verify(mockPreparedStatement).getGeneratedKeys();
    }

    @Test
    @Disabled("Not able to test because mock is reinitialized every time")
    @DisplayName("Should select ticket by id")
    public  void shouldSelectTicketByID(){
       ParkingTicket selectedTicket = parkingTicketDAO.select(expectedID).get();
       assertEquals(parkingTicketLeft,selectedTicket);
    }

    @Test
    @DisplayName("Should update parking slot id and size")
    public void shouldUpdateParkingSlot() throws SQLException {
       when(mockPreparedStatement.execute()).thenReturn(true);
       parkingTicketDAO.updateParkingSlotID(parkingTicketPresent,expectedParkingSlot);
    }

    @Test
    @DisplayName("Should delete parking ticket")
    public void shouldDeleteParkingTicket() throws SQLException {

        when(mockPreparedStatement.execute()).thenReturn(true);
        parkingTicketDAO.delete(parkingTicketLeft);
    }

    @Test
    @DisplayName("Should update parking ticket")
    public void shouldUpdateParkingTicket() throws SQLException {

        when(mockPreparedStatement.execute()).thenReturn(true);
        parkingTicketDAO.update(parkingTicketPresent,expectedID);
    }

    @AfterEach
    public void verifyCalls() throws SQLException {

        verify(connector).getDataSource();
        verify(mockDataSource).getConnection();

    }

}
