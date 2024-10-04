package org.example.strategies;

import org.example.models.Ride;

import java.util.List;

public interface RideSelectionStrategy {
    List<Ride> selectRides(List<Ride> availableRides, String origin, String destination, int seats);
}