package com.kurylchyk.model.dao.vehicles;

import com.kurylchyk.model.domain.vehicles.Motorbike;
import com.kurylchyk.model.domain.vehicles.Vehicle;
import com.kurylchyk.model.domain.vehicles.vehicleEnum.VehicleType;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class MotorbikeDAO extends VehicleDAO<Motorbike,String> {

    @Override
    public String insert(Motorbike motorbike) {
      return  super.insert(motorbike);
    }

    @Override
    public void delete(Motorbike motorbike) {
        super.delete(motorbike);
    }

    @Override
    public Optional<Motorbike> select(String licensePlate)  {

        return super.select(licensePlate);
    }

    @Override
    protected Vehicle getVehicle(ResultSet resultSet) throws SQLException {

        Vehicle vehicle = null;
        String make = resultSet.getString("make");
        String model = resultSet.getString("model");
        String licencePlate = resultSet.getString("license_plate");

        return new Motorbike(make,model,licencePlate);
    }

    @Override
    public List<Motorbike> selectAll() {
        return super.selectAll();
    }

    @Override
    protected VehicleType defineTypeOfVehicle() {
        return VehicleType.MOTORBIKE;
    }

    @Override
    public void update(Motorbike motorbike, String licensePlate) {
        super.update(motorbike,licensePlate);
    }
}
