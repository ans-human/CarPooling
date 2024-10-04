package org.example.strategies;

import org.example.models.Ride;

import java.util.List;
import java.util.stream.Collectors;

public class StrategyUtils {
    public static List<Ride> filterRidesByRoute(List<Ride> availableRides, String origin, String destination) {
        return availableRides.stream()
                .filter(ride -> ride.getOrigin().equals(origin) && ride.getDestination().equals(destination) && ride.isActive())
                .collect(Collectors.toList());
    }
}
