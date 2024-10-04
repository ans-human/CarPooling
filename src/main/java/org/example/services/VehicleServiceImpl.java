package org.example.services;

import com.google.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.example.models.Vehicle;
import org.example.validators.VehicleValidator;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Singleton
public class VehicleServiceImpl implements VehicleService {
    private final Set<Vehicle> vehicles;

    @Inject
    public VehicleServiceImpl(List<Vehicle> vehicles) {
        this.vehicles = new HashSet<>(vehicles);
    }

    @Override
    public void addVehicle(Vehicle vehicle) {
        VehicleValidator.validateVehicle(vehicle);
        if (isVehiclePresent(vehicle)) {
            throw new IllegalArgumentException("Vehicle with registration number " + vehicle.getNumber() + " already exists.");
        }
        vehicles.add(vehicle);
    }

    @Override
    public void removeVehicle(Vehicle vehicle) {
        VehicleValidator.validateVehicle(vehicle);
        if (!isVehiclePresent(vehicle)) {
            throw new IllegalArgumentException("Vehicle with registration number " + vehicle.getNumber() + " does not exist.");
        }
        vehicles.remove(vehicle);
    }

    @Override
    public boolean isVehiclePresent(Vehicle vehicle) {
        return vehicles.contains(vehicle);
    }

    @Override
    public List<Vehicle> getAllVehicles() {
        return new ArrayList<>(vehicles);
    }
}