package service;

import org.example.models.Vehicle;
import org.example.services.VehicleService;
import org.example.services.VehicleServiceImpl;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class VehicleServiceTest {

    private VehicleService vehicleService;
    private Vehicle vehicle;

    @BeforeEach
    public void setUp() {
        vehicleService = new VehicleServiceImpl(new ArrayList<>());
        vehicle = new Vehicle("Rohan", "Swift", "KA-01-12345");
    }

    @Test
    public void addVehicleAddsVehicleSuccessfully() {
        vehicleService.addVehicle(vehicle);
        assertTrue(vehicleService.isVehiclePresent(vehicle));
    }

    @Test
    public void addVehicleThrowsExceptionForDuplicateVehicle() {
        vehicleService.addVehicle(vehicle);
        assertThrows(IllegalArgumentException.class, () -> vehicleService.addVehicle(vehicle));
    }

    @Test
    public void removeVehicleRemovesVehicleSuccessfully() {
        vehicleService.addVehicle(vehicle);
        vehicleService.removeVehicle(vehicle);
        assertTrue(vehicleService.getAllVehicles().isEmpty());
    }

    @Test
    public void removeVehicleThrowsExceptionForNonExistentVehicle() {
        assertThrows(IllegalArgumentException.class, () -> vehicleService.removeVehicle(vehicle));
    }

    @Test
    public void isVehiclePresentReturnsTrueForExistingVehicle() {
        vehicleService.addVehicle(vehicle);
        assertTrue(vehicleService.isVehiclePresent(vehicle));
    }

    @Test
    public void isVehiclePresentReturnsFalseForNonExistentVehicle() {
        assertTrue(!vehicleService.isVehiclePresent(vehicle));
    }

    @Test
    public void getAllVehiclesReturnsAllVehicles() {
        vehicleService.addVehicle(vehicle);
        Collection<Vehicle> vehicles = vehicleService.getAllVehicles();
        assertEquals(1, vehicles.size());
        assertTrue(vehicles.contains(vehicle));
    }
}
