package org.example.services;

import org.example.models.Vehicle;

import java.util.Collection;

public interface VehicleService {
    void addVehicle(Vehicle vehicle);
    void removeVehicle(Vehicle vehicle);
    boolean isVehiclePresent(Vehicle vehicle);
    Collection<Vehicle> getAllVehicles();
}