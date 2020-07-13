package com.kurylchyk.model.services.impl.parkingSlotCommand;

import com.kurylchyk.model.domain.vehicles.vehicleEnum.CarSize;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.domain.parkingSlots.slotEnum.SlotSize;
import com.kurylchyk.model.domain.vehicles.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;


public class ParkingSlotDefinitionTest {

    @Nested
    @DisplayName("Should define small slot")
    class SmallSlotTest {

        @Test
        @DisplayName("Should define small slot for a car")
        public void shouldDefineSmallSlotForCar() {

            Vehicle miniCar = new Car("Smart", "ForTwo", "BC 4934 KA", CarSize.MINI_CAR);

            SlotSize currentSlotSize = ParkingSlotDefiner.defineSlotSize(miniCar);
            assertEquals(SlotSize.SMALL, currentSlotSize);
        }

        @Test
        @DisplayName("Should define small slot for  a motorbike")
        public void shouldDefineSmallSlotForMotorbike() {

            Vehicle motorbike = new Motorbike("Harley Davidson", "Iron 883", "AA 0099 KC");
            SlotSize currentSlotSize = ParkingSlotDefiner.defineSlotSize(motorbike);
            assertEquals(SlotSize.SMALL, currentSlotSize);
        }
    }

    @Nested
    @DisplayName("Should define medium slot")
    class MediumSlotTest {

        @ParameterizedTest
        @EnumSource(value=CarSize.class ,names = {"FAMILY_CAR","EXCLUSIVE_CAR","SUV","MPV"})
        @DisplayName("Should define medium slot for given car sizes")
        public void shouldDefineMediumSlotForCar(CarSize carSize) {

            Car car = new Car("Honda", "Crosstour", "CE 9248 EI", carSize);
            SlotSize currentSlotSize = ParkingSlotDefiner.defineSlotSize(car);
            assertEquals(SlotSize.MEDIUM, currentSlotSize);
        }

        @Test
        @DisplayName("Should define medium slot for truck without trailer")
        public void shouldDefineMediumSlotForTruck() {

            Truck truck = new Truck("Man", "F 2000", "BK 4329 EI", false);
            SlotSize currentSlotSize = ParkingSlotDefiner.defineSlotSize(truck);
            assertEquals(SlotSize.MEDIUM, currentSlotSize);
        }

        @Test
        @DisplayName("Should define medium slot for bus with seats less than 17")
        public void shouldDefineMediumSlotForBus() {

            Bus bus = new Bus("Mercedes-Benz", "Sprinter", "AA 3204 EK", 8);
            SlotSize currentSlotSize = ParkingSlotDefiner.defineSlotSize(bus);
            assertEquals(SlotSize.MEDIUM, currentSlotSize);
        }
    }

    @Nested
    @DisplayName("Should define large slot")
    class LargeSlotTest {


        @Test
        @DisplayName("Should define large slot for pick up car")
        public void shouldDefineLargeSlotForCar() {
            Car car = new Car("Ford", "F-150", "CE 4320 EI", CarSize.PICK_UP);
            SlotSize currentSlotSize = ParkingSlotDefiner.defineSlotSize(car);
            assertEquals(SlotSize.LARGE, currentSlotSize);
        }


        @Test
        @DisplayName("should define large slot for truck with trailer")
        public void shouldDefineLargeSlotForTruck() {
            Truck truck = new Truck("Man", "F 2000", "BK 4329 EI", true);
            SlotSize currentSlotSize = ParkingSlotDefiner.defineSlotSize(truck);
            assertEquals(SlotSize.LARGE, currentSlotSize);
        }


        @Test
        @DisplayName("Should define large slot for big bus")
        public void shouldDefineLargeSlotForBus() {

            Bus bus = new Bus("Mercedes-Benz", "Sprinter", "AA 3204 EK", 45);
            SlotSize currentSlotSize = ParkingSlotDefiner.defineSlotSize(bus);
            assertEquals(SlotSize.LARGE, currentSlotSize);
        }

    }

    @Test
    @DisplayName("Should define slot according to vehicle type")
    public void shouldDefineSlot() throws ParkingSystemException {

        Vehicle vehicle = new Motorbike("Suzuki", "GXS-R 600", "AO 3249 OK");
        DefineParkingSlotCommand defineParkingSlotCommand = new DefineParkingSlotCommand(vehicle);
        SlotSize currentSlotSize = defineParkingSlotCommand.execute();
        assertNotNull(currentSlotSize);

    }
}
