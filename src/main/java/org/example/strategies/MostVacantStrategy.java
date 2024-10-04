package org.example.strategies;

import org.example.models.Ride;

import java.util.Comparator;
import java.util.List;

public class MostVacantStrategy implements RideSelectionStrategy {

    @Override
    public List<Ride> selectRides(List<Ride> availableRides, String origin, String destination, int seats) {
        List<Ride> rides = StrategyUtils.filterRidesByRoute(availableRides, origin, destination);
        Ride selectedRide = rides.stream()
                .filter(ride -> ride.getAvailableSeats() >= seats)
                .max(Comparator.comparingInt(Ride::getAvailableSeats))
                .orElse(null);
        return selectedRide == null ? List.of() : List.of(selectedRide);
    }
}