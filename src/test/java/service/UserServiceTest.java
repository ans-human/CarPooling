package service;

import org.example.models.User;
import org.example.models.Vehicle;
import org.example.services.UserServiceImpl;
import org.example.services.VehicleService;
import org.example.services.VehicleServiceImpl;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;

public class UserServiceTest {

    private UserServiceImpl userService;
    private VehicleService vehicleService;
    private User user;
    private Vehicle vehicle;

    @BeforeEach
    public void setUp() {
        vehicleService = new VehicleServiceImpl(new ArrayList<>());
        userService = new UserServiceImpl(new ArrayList<>(), vehicleService);
        user = new User("Rohan", "M", 36, new HashSet<>(), new HashSet<>(), new HashSet<>());
        vehicle = new Vehicle("Rohan", "Swift", "KA-01-12345");
    }

    @Test
    public void addUserAddsUserSuccessfully() {
        userService.addUser(user);
        assertEquals(user, userService.getUserById("Rohan"));
    }

    @Test
    public void addUserThrowsExceptionForDuplicateUser() {
        userService.addUser(user);
        assertThrows(IllegalArgumentException.class, () -> userService.addUser(user));
    }

    @Test
    public void addVehicleAddsVehicleToUser() {
        userService.addUser(user);
        userService.addVehicle(user, vehicle);
        assertTrue(user.getVehicles().contains(vehicle));
    }

    @Test
    public void addVehicleThrowsExceptionForNonExistentUser() {
        assertThrows(IllegalArgumentException.class, () -> userService.addVehicle(user, vehicle));
    }

    @Test
    public void getUserByIdReturnsCorrectUser() {
        userService.addUser(user);
        assertEquals(user, userService.getUserById("Rohan"));
    }

    @Test
    public void getUserByIdThrowsExceptionForNonExistentUser() {
        assertThrows(IllegalArgumentException.class, () -> userService.getUserById("Sohan"));
    }

    @Test
    public void getAllUsersReturnsAllUsers() {
        userService.addUser(user);
        assertEquals(1, userService.getAllUsers().size());
    }

    @Test
    public void removeUserRemovesUserSuccessfully() {
        userService.addUser(user);
        userService.removeUser(user);
        assertThrows(IllegalArgumentException.class, () -> userService.getUserById("Mohan"));
    }

    @Test
    public void removeVehicleRemovesVehicleFromUser() {
        userService.addUser(user);
        userService.addVehicle(user, vehicle);
        userService.removeVehicle(user, vehicle);
        assertTrue(user.getVehicles().isEmpty());
    }
}