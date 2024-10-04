package org.example.strategies;

import org.example.models.Ride;
import org.example.models.RideGraph;

import java.util.*;

public class DijkstraRideSelectionStrategy implements RideSelectionStrategy {

    @Override
    public List<Ride> selectRides(List<Ride> availableRides, String origin, String destination, int seats) {
        RideGraph graph = new RideGraph();
        for (Ride ride : availableRides) {
            graph.addRide(ride);
        }

        PriorityQueue<RidePath> queue = new PriorityQueue<>(Comparator.comparingInt(RidePath::getRideChanges));
        queue.add(new RidePath(origin, new ArrayList<>(), 0));

        Set<String> visited = new HashSet<>();

        while (!queue.isEmpty()) {
            RidePath currentPath = queue.poll();
            String currentLocation = currentPath.getCurrentLocation();

            if (currentLocation.equals(destination)) {
                return currentPath.getRides();
            }

            if (visited.contains(currentLocation)) {
                continue;
            }

            visited.add(currentLocation);

            for (Ride ride : graph.getRidesFrom(currentLocation)) {
                if (ride.getAvailableSeats() >= seats) {
                    List<Ride> newPath = new ArrayList<>(currentPath.getRides());
                    newPath.add(ride);
                    queue.add(new RidePath(ride.getDestination(), newPath, currentPath.getRideChanges() + 1));
                }
            }
        }

        return new ArrayList<>();
    }

    private static class RidePath {
        private final String currentLocation;
        private final List<Ride> rides;
        private final int rideChanges;

        public RidePath(String currentLocation, List<Ride> rides, int rideChanges) {
            this.currentLocation = currentLocation;
            this.rides = rides;
            this.rideChanges = rideChanges;
        }

        public String getCurrentLocation() {
            return currentLocation;
        }

        public List<Ride> getRides() {
            return rides;
        }

        public int getRideChanges() {
            return rideChanges;
        }
    }
}