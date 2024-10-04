package org.example.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@Builder
@ToString
public class Ride {
    private User driver;
    private Vehicle vehicle;
    private String origin;
    private String destination;
    private int offeredSeats;
    private int availableSeats;
    private boolean active;

    public void endRide() {
        this.active = false;
        this.availableSeats = offeredSeats;
    }

    public void bookSeats(int seats) {
        this.availableSeats -= seats;
    }

    public void releaseOneSeat() {
        this.availableSeats++;
    }
}

