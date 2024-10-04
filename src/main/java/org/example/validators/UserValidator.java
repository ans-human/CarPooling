package org.example.validators;

import org.example.models.User;

import java.util.Collection;
import java.util.Map;

public class UserValidator {
    public static void validateUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
    }

    public static void validateUsersCollection(Object users) {
        if (users == null) {
            throw new IllegalStateException("Users list cannot be null");
        }
    }

    public static void validateUserExists(Map<String, User> users, String id) {
        if (!users.containsKey(id)) {
            throw new IllegalArgumentException("User: " + id + " not added yet!");
        }
    }

    public static void validateUserNotPresent(Collection<String> users, String id) {
        if (users.contains(id)) {
            throw new IllegalArgumentException("User: " + id + " already added!");
        }
    }
}