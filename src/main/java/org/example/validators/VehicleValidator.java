package org.example.validators;

import org.example.models.Vehicle;

public class VehicleValidator {

    public static void validateVehicle(Vehicle vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle cannot be null.");
        }
        if (vehicle.getModel() == null || vehicle.getModel().isEmpty()) {
            throw new IllegalArgumentException("Vehicle model cannot be null or empty.");
        }
        if (vehicle.getNumber() == null || vehicle.getNumber().isEmpty()) {
            throw new IllegalArgumentException("Vehicle registration number cannot be null or empty.");
        }
    }
}