package org.example.services;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.models.User;
import org.example.models.Vehicle;
import org.example.validators.UserValidator;

import javax.inject.Singleton;
import java.util.List;

@Slf4j
@Singleton
public class UserServiceImpl implements UserService {
    private List<User> users;

    @Inject
    public UserServiceImpl(List<User> users) {
        this.users = users;
    }

    public void addVehicle(User user, Vehicle vehicle) {
        UserValidator.validateUser(user);
        UserValidator.validateUserExists(users, user);
        user.getVehicles().add(vehicle);
    }

    public void addUser(User user) {
        UserValidator.validateUser(user);
        UserValidator.validateUserNotPresent(users, user);
        users.add(user);
        log.info("User: {} added successfully!", user.getId());
    }

    public void addUsers(List<User> users) {
        for (User user : users) {
            addUser(user);
        }
    }

    public User getUserById(String name) {
        UserValidator.validateUsersList(users);

        return users.stream()
                .filter(user -> user.getId().equals(name))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<User> getAllUsers() {
        return users;
    }

    public List<User> getUsers() {
        UserValidator.validateUsersList(users);
        return users;
    }

    public int getTotalRidesOffered() {
        return users.stream()
                .mapToInt(user -> user.getRidesOffered().size())
                .sum();
    }

    public int getTotalRidesTaken() {
        return users.stream()
                .mapToInt(user -> user.getRidesTaken().size())
                .sum();
    }

    public List<Vehicle> getVehicles(User user) {
        UserValidator.validateUser(user);
        return user.getVehicles();
    }

    public void removeUser(User user) {
        UserValidator.validateUser(user);
        UserValidator.validateUsersList(users);
        UserValidator.validateUserExists(users, user);
        users.remove(user);
        log.info("User: {} removed successfully!", user.getId());
    }

    public void removeVehicle(User user, Vehicle vehicle) {
        UserValidator.validateUser(user);
        UserValidator.validateUserExists(users, user);
        user.getVehicles().remove(vehicle);
        log.info("Vehicle: {} - {} removed successfully!", vehicle.getModel(), vehicle.getNumber());
    }
}