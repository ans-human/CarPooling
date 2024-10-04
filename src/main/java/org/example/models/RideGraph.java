package org.example.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RideGraph {
    private final Map<String, List<Ride>> adjacencyList = new HashMap<>();

    public void addRide(Ride ride) {
        adjacencyList.computeIfAbsent(ride.getOrigin(), k -> new ArrayList<>()).add(ride);
    }

    public List<Ride> getRidesFrom(String location) {
        return adjacencyList.getOrDefault(location, new ArrayList<>());
    }
}