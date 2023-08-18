package com.tomdud.businesstripapp.businesstripapp.service;

import com.tomdud.businesstripapp.businesstripapp.exception.UserNotAuthenticatedException;
import com.tomdud.businesstripapp.businesstripapp.exception.UserNotFoundException;
import com.tomdud.businesstripapp.businesstripapp.model.User;
import com.tomdud.businesstripapp.businesstripapp.repository.UserRepository;

import java.util.Optional;


public class UserService {

    private static volatile UserService instance;

    private final UserRepository userRepository = UserRepository.getInstance();

    public static UserService getInstance() {
        UserService result = instance;
        if (result == null) {
            synchronized (UserService.class) {
                result = instance;
                if (result == null) {
                    instance = result = new UserService();
                }
            }
        }
        return result;
    }

    public User getUserByUsername(String name) throws UserNotFoundException {
        Optional<User> optional = userRepository.getUserByUsername(name);
        if(optional.isEmpty())
            throw new UserNotFoundException(String.format("User with name %s not found in repository", name));
        else return optional.get();
    }

    public User returnUserIfPasswordIsCorrect(String name, String password) throws UserNotAuthenticatedException {
        User user;

        try {
            user = getUserByUsername(name);
        } catch (UserNotFoundException userNotFoundException) {
            throw new UserNotAuthenticatedException("Password is not correct");
        }

        if(user.getPassword().equals(password))
            return user;
        else throw new UserNotAuthenticatedException("Password is not correct");
    }

}
