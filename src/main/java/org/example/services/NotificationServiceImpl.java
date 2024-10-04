package org.example.services;

import lombok.extern.slf4j.Slf4j;
import org.example.models.Ride;
import org.example.models.User;
import org.example.observers.RideObserver;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
@Slf4j
public class NotificationServiceImpl implements NotificationService {
    private final UserService userService;
    private final List<RideObserver> observers = new ArrayList<>();

    @Inject
    public NotificationServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void notifyRideSelected(Ride ride) {
        log.info("Ride selected: {}", ride);
        for (RideObserver observer : observers) {
            observer.onRideSelected(ride);
        }
    }

    @Override
    public void notifyRideEnded(Ride ride) {
        User driver = ride.getDriver();
        log.info("Ride ended for driver: {}", driver.getName());

        for (User user : userService.getAllUsers()) {
            if (user.getRidesTaken() != null && user.getRidesTaken().contains(ride)) {
                log.info("Ride ended for ride taker: {}", user.getName());
            }
        }

        for (RideObserver observer : observers) {
            observer.onRideEnded(ride);
        }
    }

    @Override
    public void registerObserver(RideObserver observer) {
        observers.add(observer);
    }

    @Override
    public void unregisterObserver(RideObserver observer) {
        observers.remove(observer);
    }
}