package org.example.modules;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import org.example.models.Ride;
import org.example.models.User;
import org.example.models.Vehicle;
import org.example.services.NotificationService;
import org.example.services.NotificationServiceImpl;
import org.example.services.RideService;
import org.example.services.RideServiceImpl;
import org.example.services.UserService;
import org.example.services.UserServiceImpl;
import org.example.services.VehicleService;
import org.example.services.VehicleServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class AppModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(new TypeLiteral<List<User>>() {}).toInstance(new ArrayList<>());
        bind(new TypeLiteral<List<Ride>>() {}).toInstance(new ArrayList<>());
        bind(new TypeLiteral<List<Vehicle>>() {}).toInstance(new ArrayList<>());
        bind(NotificationService.class).to(NotificationServiceImpl.class);
        bind(UserService.class).to(UserServiceImpl.class);
        bind(RideService.class).to(RideServiceImpl.class);
        bind(VehicleService.class).to(VehicleServiceImpl.class);
    }
}