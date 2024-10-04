package org.example.observers;

import lombok.extern.slf4j.Slf4j;
import org.example.models.Ride;

@Slf4j
public class RideLoggerObserver implements RideObserver {
    @Override
    public void onRideSelected(Ride ride) {
//        log.info("Ride selected: {}", ride);
    }

    @Override
    public void onRideEnded(Ride ride) {
//        log.info("Ride ended: {}", ride);
    }
}