package org.example;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.example.models.Ride;
import org.example.models.User;
import org.example.models.Vehicle;
import org.example.modules.AppModule;
import org.example.observers.RideLoggerObserver;
import org.example.services.NotificationService;
import org.example.services.RideService;
import org.example.services.UserService;
import org.example.services.VehicleService;
import org.example.strategies.DijkstraRideSelectionStrategy;
import org.example.strategies.MostVacantStrategy;
import org.example.strategies.PreferredVehicleStrategy;

import java.util.HashSet;

public class CarPoolingApp {
    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("********** Car Pooling App **********");
        System.out.println("======================================");
        Injector injector = Guice.createInjector(new AppModule());

        UserService userService = injector.getInstance(UserService.class);
        VehicleService vehicleService = injector.getInstance(VehicleService.class);
        RideService rideService = injector.getInstance(RideService.class);
        NotificationService notificationService = injector.getInstance(NotificationService.class);
        notificationService.registerObserver(new RideLoggerObserver());

        onboarding(userService, rideService);
        System.out.println("Vehicles Registered: ");
        for (Vehicle vehicle : vehicleService.getAllVehicles()) {
            System.out.println(vehicle);
        }

        offerRides(rideService);

        selectRides(rideService);

        rideService.printAllOfferedRides();

        endRides(userService, rideService);

        rideService.printAllOfferedRides();

        System.out.println("=================================");
        System.out.println("********** Ride Summary **********");
        System.out.println("=================================");
        rideService.printTotalRides();
        rideService.printRideState();
    }

    private static void onboarding(UserService userService, RideService rideService) {
        userService.addUser(User.builder().name("Rohan").gender("M").age(36).ridesOffered(new HashSet<>()).ridesTaken(new HashSet<>()).vehicles(new HashSet<>()).build());
        userService.addVehicle(userService.getUserById("Rohan"), new Vehicle("Rohan", "Swift", "KA-01-12345"));

        userService.addUser(User.builder().name("Shashank").gender("M").age(29).ridesOffered(new HashSet<>()).ridesTaken(new HashSet<>()).vehicles(new HashSet<>()).build());
        userService.addVehicle(userService.getUserById("Shashank"), new Vehicle("Shashank", "Baleno", "TS-05-62395"));

        userService.addUser(User.builder().name("Nandini").gender("F").age(29).ridesOffered(new HashSet<>()).ridesTaken(new HashSet<>()).vehicles(new HashSet<>()).build());

        userService.addUser(User.builder().name("Shipra").gender("F").age(27).ridesOffered(new HashSet<>()).ridesTaken(new HashSet<>()).vehicles(new HashSet<>()).build());
        userService.addVehicle(userService.getUserById("Shipra"), new Vehicle("Shipra", "Polo", "KA-05-41491"));
        userService.addVehicle(userService.getUserById("Shipra"), new Vehicle("Shipra", "Activa", "KA-12-12332"));

        userService.addUser(User.builder().name("Gaurav").gender("M").age(29).ridesOffered(new HashSet<>()).ridesTaken(new HashSet<>()).vehicles(new HashSet<>()).build());

        userService.addUser(User.builder().name("Rahul").gender("M").age(35).ridesOffered(new HashSet<>()).ridesTaken(new HashSet<>()).vehicles(new HashSet<>()).build());
        userService.addVehicle(userService.getUserById("Rahul"), new Vehicle("Rahul", "XUV", "KA-05-1234"));

        userService.addUser(User.builder().name("Sohan").gender("M").age(35).ridesOffered(new HashSet<>()).ridesTaken(new HashSet<>()).vehicles(new HashSet<>()).build());
        userService.addVehicle(userService.getUserById("Sohan"), new Vehicle("Sohan", "Swift", "KA-01-1235"));

        userService.addUser(User.builder().name("Mohan").gender("M").age(35).ridesOffered(new HashSet<>()).ridesTaken(new HashSet<>()).vehicles(new HashSet<>()).build());
        userService.addVehicle(userService.getUserById("Mohan"), new Vehicle("Mohan", "Swift", "KA-01-1236"));
//        userService.removeVehicle(userService.getUserById("Mohan"), new Vehicle("Mohan", "Swift", "KA-01-1236"));
//        userService.addVehicle(userService.getUserById("Mohan"), new Vehicle("Mohan", "Swift", "KA-01-1236"));
//        userService.removeUser(userService.getUserById("Mohan"));
//        userService.addUser(User.builder().name("Mohan").gender("M").age(35).ridesOffered(new HashSet<>()).ridesTaken(new HashSet<>()).vehicles(new HashSet<>()).build());

        userService.addUser(User.builder().name("Anshuman").gender("M").age(24).ridesOffered(new HashSet<>()).ridesTaken(new HashSet<>()).vehicles(new HashSet<>()).build());

    }

    private static void offerRides(RideService rideService) {
        rideService.offerRide("Rohan", "Hyderabad", 1, "Swift", "KA-01-12345", "Bangalore");
        rideService.offerRide("Shipra", "Bangalore", 1, "Activa", "KA-12-12332", "Mysore");
        rideService.offerRide("Shipra", "Bangalore", 2, "Polo", "KA-05-41491", "Mysore");
        rideService.offerRide("Shashank", "Hyderabad", 2, "Baleno", "TS-05-62395", "Bangalore");
        rideService.offerRide("Rahul", "Hyderabad", 5, "XUV", "KA-05-1234", "Bangalore");
//        rideService.offerRide("Rohan", "Bangalore", 1, "Swift", "KA-01-12345", "Pune");
        rideService.offerRide("Sohan", "Delhi", 1, "Swift", "KA-01-1235", "Lucknow");
        rideService.offerRide("Mohan", "Lucknow", 1, "Swift", "KA-01-1236", "Guwahati");
    }

    private static void selectRides(RideService rideService) {
        rideService.selectRide("Nandini", "Bangalore", "Mysore", 1, new MostVacantStrategy());
        rideService.selectRide("Gaurav","Bangalore", "Mysore", 1, new PreferredVehicleStrategy("Activa"));
        rideService.selectRide("Shashank", "Mumbai", "Bangalore", 1, new MostVacantStrategy());
        rideService.selectRide("Rohan", "Hyderabad", "Bangalore", 1, new PreferredVehicleStrategy("Baleno"));
        rideService.selectRide("Shashank", "Hyderabad", "Bangalore", 1, new PreferredVehicleStrategy("Polo"));
        rideService.selectRide("Anshuman", "Delhi", "Guwahati", 1, new DijkstraRideSelectionStrategy());
    }

    private static void endRides(UserService userService, RideService rideService) {
        for (User user : userService.getAllUsers()) {
            for (Ride ride : user.getRidesOffered()) {
                rideService.endRide(ride);
            }
        }
    }
}