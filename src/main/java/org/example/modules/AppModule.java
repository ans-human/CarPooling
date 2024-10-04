package org.example.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Named;
import org.example.models.Ride;
import org.example.models.User;
import org.example.services.NotificationService;
import org.example.services.NotificationServiceImpl;
import org.example.services.RideService;
import org.example.services.RideServiceImpl;
import org.example.services.UserService;
import org.example.services.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class AppModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(new TypeLiteral<List<User>>() {}).toInstance(new ArrayList<>());
        bind(new TypeLiteral<List<Ride>>() {}).toInstance(new ArrayList<>());
        bind(NotificationService.class).to(NotificationServiceImpl.class);
        bind(UserService.class).to(UserServiceImpl.class);
        bind(RideService.class).to(RideServiceImpl.class);
    }
}