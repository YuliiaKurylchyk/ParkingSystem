package com.kurylchyk.controller;


import com.kurylchyk.model.domain.parkingTicket.ticketEnum.Status;
import com.kurylchyk.model.domain.vehicles.Car;
import com.kurylchyk.model.domain.vehicles.Vehicle;
import com.kurylchyk.model.domain.vehicles.vehicleEnum.CarSize;
import com.kurylchyk.model.domain.vehicles.vehicleEnum.VehicleType;
import com.kurylchyk.model.exceptions.NoSuchVehicleFoundException;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.services.VehicleService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class VehicleServletTest {


    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    RequestDispatcher requestDispatcher;

    @Mock
    HttpSession session;

    @Mock
    VehicleService vehicleService;

    @InjectMocks
    VehicleServlet vehicleServlet;

    private static Car vehicle;

    @BeforeAll
    public static void initAll() {

        vehicle = new Car("Ford", "F-150", "AA 0011 OI", CarSize.PICK_UP);
    }

    @BeforeEach
    public void init() throws ServletException, IOException {
        MockitoAnnotations.initMocks(this);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        doNothing().when(requestDispatcher).forward(request, response);
        doNothing().when(response).sendRedirect(anyString());
        doNothing().when(session).setAttribute("vehicle", vehicle);
        doNothing().when(request).setAttribute("vehicle", vehicle);
        when(request.getSession()).thenReturn(session);

        when(request.getParameter("vehicleID")).thenReturn(vehicle.getLicensePlate());
        when(request.getParameter("vehicleType")).thenReturn(vehicle.getVehicleType().toString());
        when(request.getParameter("status")).thenReturn(String.valueOf(Status.PRESENT));

    }

    @Test
    @DisplayName("Should get vehicle")
    public void shouldGetVehicle() throws ParkingSystemException, ServletException,
            IOException {
        when(vehicleService.find(vehicle.getLicensePlate())).thenReturn(vehicle);
        vehicleServlet.doGetVehicle(request, response);

        verify(request).getParameter("vehicleID");
    }

    @Test
    @DisplayName("Should NOT get vehicle")
    public void shouldNotGetVehicle() throws ParkingSystemException, ServletException,
            IOException {
        String wrongLicensePlate = "CE 2394 OL";
        when(request.getParameter("vehicleID")).thenReturn(wrongLicensePlate);
        when(vehicleService.find(wrongLicensePlate)).thenThrow(NoSuchVehicleFoundException.class);

        vehicleServlet.doGetVehicle(request, response);

        verify(request).setAttribute(eq("NotFound"), any(Exception.class));

    }

    @Test
    @DisplayName("Should edit vehicle")
    public void shouldEditVehicle() throws ParkingSystemException, ServletException, IOException {
        when(vehicleService.getFromDB(vehicle.getLicensePlate(),vehicle.getVehicleType())).thenReturn(vehicle);
        vehicleServlet.doEdit(request,response);
        verify(vehicleService).getFromDB(vehicle.getLicensePlate(),vehicle.getVehicleType());
        verify(session).setAttribute("vehicle",vehicle);

    }

    @Test
    @DisplayName("Should should show all")
    public void shouldShowAll() throws ParkingSystemException, ServletException, IOException {

        VehicleType expectedType = vehicle.getVehicleType();
        String expectedStatus = "ALL";
        List<Vehicle> expectedAllVehicles = new ArrayList<>(Arrays.asList(
                vehicle,
                new Car("Audi","Q3","AA 2131 OO",CarSize.SUV)));

        when(request.getParameter("status")).thenReturn(expectedStatus);
        when(vehicleService.getAll(expectedType)).thenReturn(expectedAllVehicles);
        doNothing().when(request).setAttribute(anyString(),anyCollection());

        vehicleServlet.showAll(request,response);

        verify(vehicleService).getAll(expectedType);
        verify(vehicleService,never()).getAll(any(Status.class),eq(expectedType));

    }

    @Test
    @DisplayName("Should get all PRESENT vehicles")
    public void shouldGetPresent() throws ParkingSystemException, ServletException, IOException {
        Status expectedStatus = Status.PRESENT;
        VehicleType expectedType = vehicle.getVehicleType();

        List<Vehicle> expectedAllPresent = new ArrayList<>(Arrays.asList(vehicle));

        when(vehicleService.getAll(expectedStatus,expectedType)).thenReturn(expectedAllPresent);
        when(request.getParameter("status")).thenReturn(String.valueOf(expectedStatus));
        doNothing().when(request).setAttribute(anyString(),anyCollection());

        vehicleServlet.showAll(request,response);

        verify(vehicleService,never()).getAll(expectedType);
        verify(vehicleService).getAll(expectedStatus,expectedType);

    }


    @Test
    @DisplayName("Should doGet and call doEdit")
    public void  shouldDoGet() throws ParkingSystemException, ServletException, IOException {


        when(vehicleService.getFromDB(vehicle.getLicensePlate(),vehicle.getVehicleType())).thenReturn(vehicle);
        when(request.getPathInfo()).thenReturn("/edit");


        vehicleServlet.doGet(request,response);
        verify(vehicleService).getFromDB(vehicle.getLicensePlate(),vehicle.getVehicleType());
        verify(session).setAttribute("vehicle",vehicle);

        verify(request).getPathInfo();

    }

}
