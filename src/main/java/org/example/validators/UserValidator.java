package org.example.validators;

import org.example.models.User;

import java.util.List;

public class UserValidator {
    public static void validateUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
    }

    public static void validateUsersList(List<User> users) {
        if (users == null) {
            throw new IllegalStateException("Users list cannot be null");
        }
    }

    public static void validateUserExists(List<User> users, User user) {
        if (!users.contains(user)) {
            throw new IllegalArgumentException("User: " + user.getId() + " not added yet!");
        }
    }

    public static void validateUserNotPresent(List<User> users, User user) {
        if (users.contains(user)) {
            throw new IllegalArgumentException("User: " + user.getId() + " already added!");
        }
    }
}