package com.kurylchyk.model.services.impl.vehicleCommand;

import com.kurylchyk.model.dao.VehicleDataUtil;
import com.kurylchyk.model.dao.vehicles.VehicleDAO;
import com.kurylchyk.model.domain.vehicles.vehicleEnum.VehicleType;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CountPresentVehicleTest {

    @Mock
    VehicleDataUtil vehicleDataUtil;

    @Mock
    VehicleDAO vehicleDAO;

    @InjectMocks
    CountPresentVehiclesCommand command;

    private static VehicleType vehicleType;
    private static Integer expectedCountOfBuses;
    private static Integer expectedCountOfAll;

    @BeforeAll()
    public static void initAll() {
        vehicleType = VehicleType.BUS;
        expectedCountOfBuses  =15;
        expectedCountOfAll=38;
    }

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    @DisplayName("Should count vehicle with appropriate type only")
    public void shouldCountWithType() throws ParkingSystemException {

        when(vehicleDAO.countAllPresent()).thenReturn(expectedCountOfBuses);
        command = new CountPresentVehiclesCommand(vehicleType,vehicleDAO,vehicleDataUtil);

        Integer countOfBuses  = command.execute();
        assertAll(
                ()->assertNotNull(countOfBuses),
                ()->assertEquals(expectedCountOfBuses,countOfBuses),
                ()->assertTrue(countOfBuses<=expectedCountOfAll)
        );

        verify(vehicleDAO).countAllPresent();
        verify(vehicleDataUtil,never()).countAllPresent();
    }

    @Test
    @DisplayName("Should count all vehicles")
    public void shouldCountAllVehicles() throws ParkingSystemException {
        when(vehicleDataUtil.countAllPresent()).thenReturn(expectedCountOfAll);
        command = new CountPresentVehiclesCommand(null,vehicleDAO,vehicleDataUtil);

        Integer countOfAll = command.execute();
        assertAll(
                ()->assertNotNull(countOfAll),
                ()->assertEquals(expectedCountOfAll,countOfAll),
                ()->assertTrue(countOfAll>=expectedCountOfBuses)
        );

        verify(vehicleDAO,never()).countAllPresent();
        verify(vehicleDataUtil).countAllPresent();
    }
}
