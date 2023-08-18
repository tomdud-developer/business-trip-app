package com.tomdud.businesstripapp.businesstripapp.repository;

import com.tomdud.businesstripapp.businesstripapp.exception.UserNotFoundException;
import com.tomdud.businesstripapp.businesstripapp.model.Reimbursement;
import com.tomdud.businesstripapp.businesstripapp.model.Role;
import com.tomdud.businesstripapp.businesstripapp.model.User;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class UserRepository {

    private static volatile UserRepository instance;

    private Set<User> repository;

    private UserRepository() {
        repository = new HashSet<>();
        User user = new User("user", "user", new HashSet<>(Set.of(Role.USER)));
        User user2 = new User("user2", "user2", new HashSet<>(Set.of(Role.USER)));
        User admin = new User("admin", "admin", new HashSet<>(Set.of(Role.ADMIN)));

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
