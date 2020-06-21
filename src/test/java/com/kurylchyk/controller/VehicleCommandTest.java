package com.kurylchyk.controller;

import com.kurylchyk.model.dao.VehicleDAO;
import com.kurylchyk.model.services.impl.vehicleCommand.CreateVehicleCommand;
import com.kurylchyk.model.services.impl.vehicleCommand.ValidateVehicleCommand;
import com.kurylchyk.model.services.impl.vehicleCommand.VehicleIsPresentCommand;
import com.kurylchyk.model.vehicles.TypeOfVehicle;
import com.kurylchyk.model.vehicles.Vehicle;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;

import java.util.*;

import static org.mockito.Mockito.when;

public class VehicleCommandTest {

   @Test
   public void shouldCreateVehicle() throws Exception {

       String make = "Honda";
       String model = "Accord";
       String licencePlate = "CE 6463 IO";
       TypeOfVehicle typeOfVehicle = TypeOfVehicle.CAR;

       CreateVehicleCommand createVehicleCommand = new CreateVehicleCommand(make,model,licencePlate,typeOfVehicle);

       Vehicle vehicle = createVehicleCommand.execute();

       Assert.assertEquals(make,vehicle.getMake());
       Assert.assertEquals(model,vehicle.getModel());
       Assert.assertEquals(licencePlate,vehicle.getLicensePlate());
       Assert.assertEquals(typeOfVehicle,vehicle.getTypeOfVehicle());
   }

    @Test
    public void shouldNotValidate() throws Exception {
        ValidateVehicleCommand validateVehicleCommand = new ValidateVehicleCommand("Toyota",
                "Sequoia","bad_info", TypeOfVehicle.CAR.toString());

        List<String> list =  validateVehicleCommand.execute();
        Assert.assertEquals(list.size(),1);

    }

    @Test
    public void shouldValidate() throws  Exception{
        ValidateVehicleCommand validateVehicleCommand = new ValidateVehicleCommand("Toyota",
                "Sequoia","BK 5436 OP", TypeOfVehicle.CAR.toString());

        List<String> list =  validateVehicleCommand.execute();
        Assert.assertEquals(list.size(),0);
    }
}
