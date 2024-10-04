package service;

import org.example.models.Ride;
import org.example.models.User;
import org.example.models.Vehicle;
import org.example.services.NotificationServiceImpl;
import org.example.services.RideServiceImpl;
import org.example.services.UserServiceImpl;
import static org.junit.Assert.assertFalse;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class RideServiceTest {

    @Test
    public void testOfferRide() {
        RideServiceImpl rideService = new RideServiceImpl(new UserServiceImpl(new ArrayList<>()), new ArrayList<>(),
                new NotificationServiceImpl(new UserServiceImpl(new ArrayList<>())));
        User user = new User("Rohan", "M", 36, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Vehicle swift = new Vehicle("Rohan", "Swift", "KA-01-12345");
        user.getVehicles().add(swift);

        Ride ride = Ride.builder().driver(user).vehicle(swift).origin("Hyderabad").destination("Bangalore")
                .availableSeats(1).active(true).build();
        rideService.offerRide("Rohan", "Hyderabad", 1, "Swift", "KA-01-12345", "Bangalore");
        assertFalse(rideService.getAvailableRides().isEmpty());
    }
}

