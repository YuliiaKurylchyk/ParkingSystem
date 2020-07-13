package com.kurylchyk.model.services;


import com.kurylchyk.model.services.impl.ServiceFacade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Testing  facade class")
public class ServiceFacadeTest {


    @Test
    @DisplayName("Should return instance of parking ticket service")
    public void shouldReturnParkingTicketService(){

        Object objectUnderTest = ServiceFacade.forParkingTicket();
        assertTrue(objectUnderTest instanceof ParkingTicketService,()->
                    "Wrong type returned");

    }


    @Test
    @DisplayName("Should return instance of parking slot service")
    public void shouldReturnParkingSlotService(){
        Object objectUnderTest = ServiceFacade.forParkingSlot();
        assertTrue(objectUnderTest instanceof ParkingSlotService,()->
                "Wrong type returned");
    }

    @Test
    @DisplayName("Should return instance of vehicle service")
    public void shouldReturnVehicleService(){
        Object objectUnderTest = ServiceFacade.forVehicle();
        assertTrue(objectUnderTest instanceof VehicleService,()->
                "Wrong type returned");
    }


    @Test
    @DisplayName("Should return instance of customer service")
    public void shouldReturnCustomerService(){
        Object objectUnderTest = ServiceFacade.forCustomer();
        assertTrue(objectUnderTest instanceof CustomerService,()->
                "Wrong type returned");
    }
}
