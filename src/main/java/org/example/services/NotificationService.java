package org.example.services;

import org.example.models.Ride;
import org.example.observers.RideObserver;

public interface NotificationService {
    void notifyRideSelected(Ride ride);
    void notifyRideEnded(Ride ride);
    void registerObserver(RideObserver observer);
    void unregisterObserver(RideObserver observer);
}