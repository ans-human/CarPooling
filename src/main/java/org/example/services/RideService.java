package org.example.services;

import org.example.models.Ride;
import org.example.observers.RideObserver;
import org.example.strategies.RideSelectionStrategy;

import java.util.List;

public interface RideService {
    void offerRide(String userName, String origin, int availableSeats, String vehicleModel, String registrationNumber,
                   String destination);

    List<Ride> selectRide(String userName, String origin, String destination, int seats, RideSelectionStrategy strategy);

    void endRide(Ride ride);

    void printRideState();

    List<Ride> getAvailableRides();

    void printTotalRides();
}