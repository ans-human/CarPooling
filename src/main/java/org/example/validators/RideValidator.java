package org.example.validators;

import lombok.extern.slf4j.Slf4j;
import org.example.models.Ride;
import org.example.models.User;
import org.example.models.Vehicle;

import java.util.List;

@Slf4j
public class RideValidator {

    public static boolean isValidVehicle(Vehicle vehicle, String vehicleModel, String registrationNumber, String userName) {

        if (vehicle == null) {
            System.out.println("Vehicle " + vehicleModel + " with registration number " + registrationNumber + " not found for user " + userName);
            return false;
        }
        return true;
    }

    public static boolean isVehicleInUse(Vehicle vehicle, List<Ride> availableRides) {
        if (availableRides.stream().anyMatch(ride -> ride.getVehicle().equals(vehicle) && ride.isActive())) {
            System.out.println("Ride already offered for this vehicle");
            return true;
        }
        return false;
    }

    public static boolean hasExistingRide(User user, Vehicle vehicle) {
        if (user.getRidesOffered().stream().anyMatch(ride -> ride.getVehicle().equals(vehicle) && ride.isActive())) {
            log.info("User: {} has already offered a ride for vehicle: {}", user, vehicle);
            return true;
        }
        return false;
    }
}