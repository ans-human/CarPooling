package org.example.observers;

import org.example.models.Ride;

public interface RideObserver {

    void onRideSelected(Ride ride);

    void onRideEnded(Ride ride);
}