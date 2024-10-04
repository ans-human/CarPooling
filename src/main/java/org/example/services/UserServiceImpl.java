package org.example.services;

import com.google.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.example.models.User;
import org.example.models.Vehicle;
import org.example.validators.UserValidator;

import javax.inject.Singleton;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Singleton
public class UserServiceImpl implements UserService {
    private final Map<String, User> users;
    private final VehicleService vehicleService;

    @Inject
    public UserServiceImpl(List<User> userList, VehicleService vehicleService) {
        this.users = new HashMap<>();
        for (User user : userList) {
            users.put(user.getId(), user);
        }
        this.vehicleService = vehicleService;
    }

    @Override
    public void addUser(User user) {
        UserValidator.validateUser(user);
        UserValidator.validateUserNotPresent(users.keySet(), user.getId());
        users.put(user.getId(), user);
        log.info("User: {} added successfully!", user.getId());
    }

    @Override
    public void addVehicle(User user, Vehicle vehicle) {
        UserValidator.validateUser(user);
        UserValidator.validateUsersCollection(users);
        UserValidator.validateUserExists(users, user.getId());
        vehicleService.addVehicle(vehicle);
        user.getVehicles().add(vehicle);
    }

    public void addUsers(List<User> users) {
        for (User user : users) {
            addUser(user);
        }
    }

    public User getUserById(String id) {
        UserValidator.validateUsersCollection(users);
        UserValidator.validateUserExists(users, id);
        return users.get(id);
    }

    @Override
    public Collection<User> getAllUsers() {
        UserValidator.validateUsersCollection(users);
        return users.values();
    }

    public Set<Vehicle> getVehicles(User user) {
        UserValidator.validateUser(user);
        return user.getVehicles();
    }

    public void removeUser(User user) {
        UserValidator.validateUser(user);
        UserValidator.validateUsersCollection(users);
        UserValidator.validateUserExists(users, user.getId());
        users.remove(user.getId());
        log.info("User: {} removed successfully!", user.getId());
    }

    public void removeVehicle(User user, Vehicle vehicle) {
        UserValidator.validateUser(user);
        UserValidator.validateUsersCollection(users);
        UserValidator.validateUserExists(users, user.getId());
        vehicleService.removeVehicle(vehicle);
        user.getVehicles().remove(vehicle);
        log.info("Vehicle: {} removed from user: {}", vehicle.getModel(), user.getId());
    }
}