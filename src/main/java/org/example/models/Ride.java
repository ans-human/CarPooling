package org.example.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Ride {
    private User driver;

    @EqualsAndHashCode.Include
    private Vehicle vehicle;

    @EqualsAndHashCode.Include
    private String origin;

    @EqualsAndHashCode.Include
    private String destination;

    @EqualsAndHashCode.Include
    private boolean active;

    private int offeredSeats;
    private int availableSeats;


    public void endRide() {
        this.active = false;
        this.availableSeats = offeredSeats;
    }

    public void bookSeats(int seats) {
        this.availableSeats -= seats;
    }
}

