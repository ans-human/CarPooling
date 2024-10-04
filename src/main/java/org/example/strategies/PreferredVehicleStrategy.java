package org.example.strategies;

import org.example.models.Ride;

import java.util.ArrayList;
import java.util.List;

public class PreferredVehicleStrategy implements RideSelectionStrategy {
    private final String preferredVehicleModel;

    public PreferredVehicleStrategy(String preferredVehicleModel) {
        this.preferredVehicleModel = preferredVehicleModel;
    }

    @Override
    public List<Ride> selectRides(List<Ride> availableRides, String origin, String destination, int seats) {
        List<Ride> rides = StrategyUtils.filterRidesByRoute(availableRides, origin, destination);
        Ride selectedRide = rides.stream()
                .filter(ride -> ride.getAvailableSeats() >= seats && ride.getVehicle().getModel().equals(preferredVehicleModel))
                .findFirst()
                .orElse(null);
        return selectedRide == null ? List.of() : List.of(selectedRide);
    }
}