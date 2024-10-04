package service;

import org.example.models.User;
import org.example.models.Ride;
import org.example.models.Vehicle;
import org.example.services.NotificationServiceImpl;
import org.example.services.RideServiceImpl;
import org.example.services.UserService;
import org.example.services.UserServiceImpl;
import org.example.services.VehicleService;
import org.example.services.VehicleServiceImpl;
import static org.junit.Assert.assertFalse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;

import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;

public class RideServiceTest {

    private UserService userService;
    private RideServiceImpl rideService;
    private VehicleService vehicleService;
    private User user;
    private Vehicle swift;

    @BeforeEach
    public void setUp() {
        userService = new UserServiceImpl(new ArrayList<>(), new VehicleServiceImpl(new ArrayList<>()));
        vehicleService = new VehicleServiceImpl(new ArrayList<>());
        rideService = new RideServiceImpl(userService, vehicleService, new ArrayList<>(), new NotificationServiceImpl(userService));
        user = new User("Rohan", "M", 36, new HashSet<>(), new HashSet<>(), new HashSet<>());
        userService.addUser(user);
        swift = new Vehicle("Rohan", "Swift", "KA-01-12345");
        user.getVehicles().add(swift);
    }

    @Test
    public void offerRideAddsRideToAvailableRides() {
        rideService.offerRide("Rohan", "Hyderabad", 1, "Swift", "KA-01-12345", "Bangalore");
        assertFalse(rideService.getAvailableRides().isEmpty());
    }

    @Test
    public void offerRideWithInvalidUserThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            rideService.offerRide("InvalidUser", "Hyderabad", 1, "Swift", "KA-01-12345", "Bangalore");
        });
    }

    @Test
    public void offerRideWithInvalidVehicleThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            rideService.offerRide("Rohan", "Hyderabad", 1, "InvalidModel", "InvalidNumber", "Bangalore");
        });
    }

    @Test
    public void selectRideBooksSeatsAndNotifies() {
        rideService.offerRide("Rohan", "Hyderabad", 2, "Swift", "KA-01-12345", "Bangalore");
        List<Ride> selectedRides = rideService.selectRide("Rohan", "Hyderabad", "Bangalore", 1, (rides, origin, destination, seats) -> rides);
        assertEquals(1, selectedRides.get(0).getAvailableSeats());
    }

    @Test
    public void endRideMarksRideAsInactive() {
        rideService.offerRide("Rohan", "Hyderabad", 1, "Swift", "KA-01-12345", "Bangalore");
        Ride ride = rideService.getAvailableRides().get(0);
        rideService.endRide(ride);
        assertFalse(ride.isActive());
    }

    @Test
    public void printRideStatePrintsCorrectly() {
        rideService.offerRide("Rohan", "Hyderabad", 1, "Swift", "KA-01-12345", "Bangalore");
        rideService.endRide(rideService.getAvailableRides().get(0));
        rideService.printRideState();
    }

    @Test
    public void printTotalRidesPrintsCorrectly() {
        rideService.offerRide("Rohan", "Hyderabad", 1, "Swift", "KA-01-12345", "Bangalore");
        rideService.endRide(rideService.getAvailableRides().get(0));
        rideService.printTotalRides();
    }

    @Test
    public void printAllOfferedRidesPrintsCorrectly() {
        rideService.offerRide("Rohan", "Hyderabad", 1, "Swift", "KA-01-12345", "Bangalore");
        rideService.printAllOfferedRides();
    }
}

