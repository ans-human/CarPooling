package org.example.services;

import com.google.inject.Inject;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.example.models.Ride;
import org.example.models.User;
import org.example.models.Vehicle;
import org.example.strategies.RideSelectionStrategy;
import org.example.validators.RideValidator;
import org.example.validators.UserValidator;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Getter
@Slf4j
public class RideServiceImpl implements RideService {
    private final UserService userService;
    private final VehicleService vehicleService;
    private final List<Ride> availableRides;
    private final Lock lock = new ReentrantLock();
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final NotificationService notificationService;

    @Inject
    public RideServiceImpl(UserService userService, VehicleService vehicleService, List<Ride> availableRides, NotificationService notificationService) {
        this.userService = userService;
        this.vehicleService = vehicleService;
        this.availableRides = availableRides;
        this.notificationService = notificationService;
    }

    public void offerRide(String userName, String origin, int offeredSeats, String vehicleModel,
                          String registrationNumber, String destination) {
        User user = userService.getUserById(userName);
        UserValidator.validateUser(user);

        Vehicle vehicle = user.getVehicles().stream()
                .filter(v -> v.getModel().equals(vehicleModel) && v.getNumber().equals(registrationNumber))
                .findFirst()
                .orElse(null);

        RideValidator.validateNonNull(vehicle, vehicleModel, registrationNumber, userName);

        RideValidator.noExistingRide(user, vehicle);

        Ride ride = Ride.builder()
                .driver(user)
                .vehicle(vehicle)
                .origin(origin)
                .destination(destination)
                .offeredSeats(offeredSeats)
                .availableSeats(offeredSeats)
                .active(true)
                .build();

        availableRides.add(ride);
        user.getRidesOffered().add(ride);
    }

    private void bookSelectedRide(User user, Ride selectedRide, int seats) {
        readWriteLock.writeLock().lock();
        try {
            selectedRide.bookSeats(seats);
            user.getRidesTaken().add(selectedRide);
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    @Override
    public List<Ride> selectRide(String userName, String origin, String destination, int seats, RideSelectionStrategy strategy) {

        List<Ride> selectedRides = getRidesByStrategy(origin, destination, seats, strategy);

        if (!selectedRides.isEmpty()) {
            User user = userService.getUserById(userName);
            UserValidator.validateUser(user);

            for (Ride ride : selectedRides) {
                bookSelectedRide(user, ride, seats);
                notificationService.notifyRideSelected(ride);
            }

            log.info("{}'s rides: {}", userName, selectedRides);
        } else {
            log.info("{}'s rides not found for route: {} to {}", userName, origin, destination);
        }

        return selectedRides;
    }

    private List<Ride> getRidesByStrategy(String origin, String destination, int seats, RideSelectionStrategy strategy) {
        List<Ride> selectedRides;
        readWriteLock.readLock().lock();
        try {
            selectedRides = strategy.selectRides(availableRides, origin, destination, seats);
        } finally {
            readWriteLock.readLock().unlock();
        }
        return selectedRides;
    }

    public void endRide(Ride ride) {
        ride.endRide();
        notificationService.notifyRideEnded(ride);
    }

    public void printRideState() {
        Collection<User> users = userService.getAllUsers();

        for (User user : users) {
            long ridesTaken = user.getRidesTaken() == null ? 0 : user.getRidesTaken().stream().filter(ride -> !ride.isActive()).count();
            long ridesOffered = user.getRidesOffered() == null ? 0 : user.getRidesOffered().size();
            System.out.println(user.getName() + ": " + ridesTaken + " Taken, " + ridesOffered + " Offered");
        }
    }

    public void printTotalRides() {
        int totalRidesOffered = getTotalRidesOffered();
        int totalRidesTaken = getTotalRidesTaken();
        System.out.println("Total Rides Offered: " + totalRidesOffered);
        System.out.println("Total Rides Taken: " + totalRidesTaken);
    }

    public void printAllOfferedRides() {
        System.out.println("All Offered Rides: ");
        availableRides.forEach(System.out::println);
    }

    private int getTotalRidesOffered() {
        return userService.getAllUsers().stream()
                .mapToInt(user -> user.getRidesOffered().size())
                .sum();
    }

    private int getTotalRidesTaken() {
        return userService.getAllUsers().stream()
                .mapToInt(user -> user.getRidesTaken().size())
                .sum();
    }
}