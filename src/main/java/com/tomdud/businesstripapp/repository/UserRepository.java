package com.tomdud.businesstripapp.repository;

import com.tomdud.businesstripapp.util.Role;
import com.tomdud.businesstripapp.model.User;

import java.util.*;

public class UserRepository {

    private static volatile UserRepository instance;

    private Set<User> repository;

    private UserRepository() {
        repository = new HashSet<>();
        User user = new User(0L, "user", "user", Role.USER);
        User user2 = new User(1L,"user2", "user2", Role.USER);
        User admin = new User(2L, "admin", "admin", Role.ADMIN);

        repository.add(user);
        repository.add(user2);
        repository.add(admin);
    }

    public static UserRepository getInstance() {
        UserRepository result = instance;
        if (result == null) {
            synchronized (UserRepository.class) {
                result = instance;
                if (result == null) {
                    instance = result = new UserRepository();
                }
            }
        }
        return result;
    }

    public Optional<User> getUserByUsername(String name) {
        return repository.stream()
                .filter(user -> user.getUsername().equals(name))
                .findFirst();
    }

}
