package com.kurylchyk.model.dao;

import com.kurylchyk.model.domain.parkingSlots.ParkingSlot;
import com.kurylchyk.model.domain.parkingSlots.slotEnum.SlotSize;
import com.kurylchyk.model.domain.parkingSlots.slotEnum.SlotStatus;
import com.kurylchyk.model.services.impl.parkingSlotDTO.ParkingSlotDTO;
import org.junit.jupiter.api.*;
import org.mockito.*;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

public class ParkingSlotDAOTest {


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
    private ParkingSlotDAO parkingSlotDAO;

    private Integer expectedID = 1;
    private SlotSize expectedSlotSize = SlotSize.MEDIUM;
    private SlotStatus expectedSlotStatus = SlotStatus.VACANT;
    private Integer expectedPrice = 15;
    private ParkingSlot parkingSlot;
    private ParkingSlotDTO parkingSlotDTO;


    @BeforeEach
    public void init() throws SQLException {
        MockitoAnnotations.initMocks(this);

        parkingSlot = new ParkingSlot(expectedID, expectedSlotSize, expectedSlotStatus, expectedPrice);
        parkingSlotDTO = new ParkingSlotDTO(expectedSlotSize, expectedID);
        when(connector.getDataSource()).thenReturn(mockDataSource);
        when(mockDataSource.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        doNothing().when(mockPreparedStatement).setString(anyInt(), anyString());
        doNothing().when(mockPreparedStatement).setInt(anyInt(), anyInt());
        when(mockPreparedStatement.execute()).thenReturn(true);
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);

        when(mockResultSet.getString("size")).thenReturn(parkingSlot.getSizeOfSlot().toString());
        when(mockResultSet.getString("slot_status")).thenReturn(parkingSlot.getStatus().toString());
        when(mockResultSet.getInt("parking_slot_id")).thenReturn(parkingSlot.getParkingSlotID());
        when(mockResultSet.getInt("price")).thenReturn(parkingSlot.getPrice());
    }


    @Test
    @DisplayName("Should select parking slot from database parking slot DTO")
    public void shouldSelectParkingSlot() throws SQLException {

        when(mockResultSet.next()).thenReturn(true);
        ParkingSlot selectedSlot = parkingSlotDAO.select(parkingSlotDTO).get();

        assertAll(

                () -> assertNotNull(selectedSlot),
                () -> assertEquals(parkingSlot, selectedSlot)
        );
        InOrder inOrder = Mockito.inOrder(mockResultSet);
        inOrder.verify(mockResultSet).next();
        inOrder.verify(mockResultSet).getString("size");
        inOrder.verify(mockResultSet).getString("slot_status");
        inOrder.verify(mockResultSet).getInt("parking_slot_id");
        inOrder.verify(mockResultSet).getInt("price");
    }


    @Test
    @DisplayName("Should insert new parking slot to database")
    public void shouldInsertToDatabase() {


        ParkingSlotDTO parkingSlotDTO = parkingSlotDAO.insert(parkingSlot);

        assertAll(
                () -> assertNotNull(parkingSlotDTO),
                () -> assertEquals(parkingSlotDTO.getParkingSlotID(), parkingSlot.getParkingSlotID()),
                () -> assertEquals(parkingSlotDTO.getSlotSize(), parkingSlot.getSizeOfSlot()));
    }


    @Test
    @DisplayName("Should update parking slot")
    public void shouldUpdateParkingSlot() {

        parkingSlotDAO.update(parkingSlot, parkingSlotDTO);
    }

    @Test
    @DisplayName("Should delete customer")
    public void shouldDeleteParkingSlot() {
        parkingSlotDAO.delete(parkingSlot);
    }


    @Nested
    class SelectingAll{

        @Test
        @DisplayName("Should select all parking slots")
        public void shouldSelectAll() throws SQLException {

            when(mockResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
            List<ParkingSlot> allParkingSlots = parkingSlotDAO.selectAll();

            assertAll(
                    ()->assertNotNull(allParkingSlots),
                    ()->assertTrue(allParkingSlots.size()==2)
            );
        }

        @Test
        @DisplayName("Should select all parking slots with appropriate size")
        public void shouldSelectAllMediumSlots() throws SQLException {

            SlotSize slotSize = SlotSize.MEDIUM;
            when(mockResultSet.next()).thenReturn(true).thenReturn(false);
            List<ParkingSlot> allParkingSlots = parkingSlotDAO.selectAll(slotSize);

            assertAll(
                    ()->assertNotNull(allParkingSlots),
                    ()->assertTrue(allParkingSlots.size()==1),
            ()->assertTrue(allParkingSlots.get(0).getSizeOfSlot().equals(slotSize))
            );
        }
    }

    @Nested
    @DisplayName("Testing operation with prices")
    class TestingPrices{

        @Test
        @DisplayName("Should update the price")
        public void shouldUpdatePrice(){

            parkingSlotDAO.updatePrice(expectedSlotSize,expectedPrice);

        }
        @Test
        @DisplayName("should get slots prices")
        public void shouldGetSlotPrices() throws SQLException {

            when(mockResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
            when(mockResultSet.getString("size")).thenReturn("SMALL").thenReturn("MEDIUM").thenReturn("LARGE");
            when(mockResultSet.getInt("price")).thenReturn(15).thenReturn(25).thenReturn(45);

            Map<SlotSize,Integer> allPrices = parkingSlotDAO.getSlotsPrice();

            assertAll(
                    ()->assertNotNull(allPrices),
                    ()->assertTrue(allPrices.size()==3),
                    ()->assertTrue(allPrices.get(SlotSize.SMALL).equals(15)),
                    ()->assertTrue(allPrices.get(SlotSize.MEDIUM).equals(25)),
                    ()->assertTrue(allPrices.get(SlotSize.LARGE).equals(45))
            );
        }


    }


    @Test
    @DisplayName("Should get last id of appropriate slot")
    public void shouldGetLastID() throws SQLException {
        Integer expectedID = 15;
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt("LAST_ID")).thenReturn(expectedID);

        Integer lastID = parkingSlotDAO.getLastID(expectedSlotSize);

        assertAll(

                ()->assertNotNull(lastID),
                ()->assertEquals(expectedID,lastID));
    }

    @AfterEach
    public void verifyCalls() throws SQLException {

        verify(connector).getDataSource();
        verify(mockDataSource).getConnection();

    }

}
