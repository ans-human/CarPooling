package org.example.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private String name;
    private String gender;
    private Integer age;
    private Set<Vehicle> vehicles;
    private Set<Ride> ridesOffered;
    private Set<Ride> ridesTaken;

    @Override
    public String toString() {
        return this.name;
    }

    public String getId() {
        return this.name;
    }
}

