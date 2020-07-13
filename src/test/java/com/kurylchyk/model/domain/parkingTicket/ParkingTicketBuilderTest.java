package com.kurylchyk.model.domain.parkingTicket;

import com.kurylchyk.model.domain.customer.Customer;
import com.kurylchyk.model.domain.parkingSlots.ParkingSlot;
import com.kurylchyk.model.domain.parkingSlots.slotEnum.SlotSize;
import com.kurylchyk.model.domain.parkingSlots.slotEnum.SlotStatus;
import com.kurylchyk.model.domain.vehicles.Motorbike;
import com.kurylchyk.model.domain.vehicles.Vehicle;
import org.junit.jupiter.api.*;

import com.kurylchyk.model.domain.parkingTicket.ticketEnum.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Parking ticket builder under test")
public class ParkingTicketBuilderTest {


    private static Vehicle expectedVehicle;
    private static Customer expectedCustomer;
    private static ParkingSlot expectedParkingSlot;

    private ParkingTicket parkingTicket;
    @BeforeAll
    public static void initAll(){

        expectedVehicle = new Motorbike("Harley Davidson", "Iron 883", "AK 2492 OP");
        expectedCustomer = Customer.newCustomer().setName("Name").setSurname("surname")
                .setPhoneNumber("+380934125789").buildCustomer();
        expectedParkingSlot = new ParkingSlot(1, SlotSize.SMALL, SlotStatus.VACANT);
    }

    @Test
    @DisplayName("Should contain only vehicle information")
    public void shouldBuildWithVehicleOnly() {


        parkingTicket = ParkingTicket.newParkingTicket().withVehicle(expectedVehicle).buildTicket();

        assertAll(
                () -> assertEquals(expectedVehicle, parkingTicket.getVehicle()),
                () -> assertNull(parkingTicket.getParkingTicketID()),
                () -> assertNull(parkingTicket.getCustomer()),
                () -> assertNull(parkingTicket.getParkingSlot()),
                () -> assertNull(parkingTicket.getStatus()),
                () -> assertNull(parkingTicket.getCost()),
                () -> assertNull(parkingTicket.getArrivalTime()),
                () -> assertNull(parkingTicket.getDepartureTime())
        );
    }

    @Test
    @DisplayName("Should contain only customer information")
    public void shouldBuildWithCustomerOnly() {

        parkingTicket = ParkingTicket.newParkingTicket().withCustomer(expectedCustomer).buildTicket();

        assertAll(
                () -> assertEquals(expectedCustomer, parkingTicket.getCustomer()),
                () -> assertNull(parkingTicket.getParkingTicketID()),
                () -> assertNull(parkingTicket.getVehicle()),
                () -> assertNull(parkingTicket.getParkingSlot()),
                () -> assertNull(parkingTicket.getStatus()),
                () -> assertNull(parkingTicket.getCost()),
                () -> assertNull(parkingTicket.getArrivalTime()),
                () -> assertNull(parkingTicket.getDepartureTime())
        );
    }


    @Test
    @DisplayName("Should contain only parking slot information")
    public void shouldBuildWithParkingSlotOnly() {
        parkingTicket = ParkingTicket.newParkingTicket().withParkingSlot(expectedParkingSlot).buildTicket();

        assertAll(
                () -> assertEquals(expectedParkingSlot, parkingTicket.getParkingSlot()),
                () -> assertNull(parkingTicket.getParkingTicketID()),
                () -> assertNull(parkingTicket.getVehicle()),
                () -> assertNull(parkingTicket.getCustomer()),
                () -> assertNull(parkingTicket.getStatus()),
                () -> assertNull(parkingTicket.getCost()),
                () -> assertNull(parkingTicket.getArrivalTime()),
                () -> assertNull(parkingTicket.getDepartureTime())
        );
    }


    @Test
    @DisplayName("Should be a recently created parking ticket")
    public void shouldBeRecentlyCreatedParkingTicket() {

        Integer expectedParkingTicketID = 1;
        LocalDateTime arrivalTime = LocalDateTime.now();
        Status currentStatus = Status.PRESENT;
         parkingTicket = ParkingTicket.newParkingTicket()
                 .withParkingTicketID(expectedParkingTicketID)
                 .withVehicle(expectedVehicle)
                 .withCustomer(expectedCustomer)
                 .withParkingSlot(expectedParkingSlot)
                 .withStatus(currentStatus)
                 .withArrivalTime(arrivalTime)
                 .buildTicket();

        assertAll(
                () -> assertEquals(expectedParkingSlot, parkingTicket.getParkingSlot()),
                () -> assertEquals(expectedParkingTicketID,parkingTicket.getParkingTicketID()),
                () -> assertEquals(expectedVehicle,parkingTicket.getVehicle()),
                () -> assertEquals(expectedCustomer,parkingTicket.getCustomer()),
                () -> assertEquals(currentStatus,parkingTicket.getStatus()),
                () -> assertNull(parkingTicket.getCost()),
                () -> assertEquals(arrivalTime,parkingTicket.getArrivalTime()),
                () -> assertNull(parkingTicket.getDepartureTime())
        );

    }


    @Test
    @DisplayName("Should be a parking ticket with all information present")

    public void shouldBeCompletedParkingTicket(){
        Integer expectedParkingTicketID = 1;
        LocalDateTime arrivalTime = LocalDateTime.now().minusMonths(1);
        Status currentStatus = Status.LEFT;
        BigDecimal expectedCost = new BigDecimal(15*30);
        LocalDateTime departureTime = LocalDateTime.now();

        parkingTicket = ParkingTicket.newParkingTicket()
                .withParkingTicketID(expectedParkingTicketID)
                .withVehicle(expectedVehicle)
                .withCustomer(expectedCustomer)
                .withParkingSlot(expectedParkingSlot)
                .withStatus(currentStatus)
                .withArrivalTime(arrivalTime)
                .withCost(expectedCost)
                .withDepartureTime(departureTime)
                .buildTicket();

        assertAll(
                () -> assertEquals(expectedParkingSlot, parkingTicket.getParkingSlot()),
                () -> assertEquals(expectedParkingTicketID,parkingTicket.getParkingTicketID()),
                () -> assertEquals(expectedVehicle,parkingTicket.getVehicle()),
                () -> assertEquals(expectedCustomer,parkingTicket.getCustomer()),
                () -> assertEquals(currentStatus,parkingTicket.getStatus()),
                () -> assertEquals(expectedCost,parkingTicket.getCost()),
                () -> assertEquals(arrivalTime,parkingTicket.getArrivalTime()),
                () -> assertEquals(departureTime,parkingTicket.getDepartureTime())
        );


    }


    @AfterEach
    public void tearDown(){
        parkingTicket = null;
    }
}
