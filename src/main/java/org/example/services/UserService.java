package org.example.services;

import org.example.models.User;
import org.example.models.Vehicle;

import java.util.Collection;

public interface UserService {

    void addUser(User user);

    void addVehicle(User user, Vehicle vehicle);

    void removeUser(User user);

    void removeVehicle(User user, Vehicle vehicle);

    User getUserById(String Id);

    Collection<User> getAllUsers();
}