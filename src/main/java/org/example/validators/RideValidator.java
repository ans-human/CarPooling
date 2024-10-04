package org.example.validators;

import lombok.extern.slf4j.Slf4j;
import org.example.models.User;
import org.example.models.Vehicle;

@Slf4j
public class RideValidator {

    public static void validateNonNull(Vehicle vehicle, String vehicleModel, String registrationNumber, String userName) {
        if (vehicle == null) {
            log.error("Vehicle: {} - number: {} not found for user: {}", vehicleModel, registrationNumber, userName);
            throw new IllegalArgumentException("Vehicle " + vehicleModel + " with registration number " + registrationNumber + " not found for user " + userName);
        }
    }

    public static void noExistingRide(User user, Vehicle vehicle) {
        if (user.getRidesOffered().stream().anyMatch(ride -> ride.getVehicle().equals(vehicle) && ride.isActive())) {
            log.error("User: {} has already offered a ride for vehicle: {}", user, vehicle);
            throw new IllegalArgumentException("User: " + user + " has already offered a ride for vehicle: " + vehicle);
        }
    }
}