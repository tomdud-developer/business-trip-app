package com.tomdud.businesstripapp.service;

import com.tomdud.businesstripapp.exception.UserNotAuthenticatedException;
import com.tomdud.businesstripapp.exception.UserNotFoundException;
import com.tomdud.businesstripapp.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UserServiceTest {

    UserService userService = UserService.getInstance();

    @Test
    void getInstance() {
        Assertions.assertEquals(UserService.getInstance(), UserService.getInstance());
    }

    @Test
    void getUserByUsername() {
        //given
        String name = "user";

        //when
        User user = userService.getUserByUsername(name);

        //then
        Assertions.assertNotNull(user);
    }


    @Test
    void getUserByUsernameShouldThrowIfUserNotFound() {
        //given
        String name = "username-who-is-not-in-repository";

        //then
        Assertions.assertThrows(
                UserNotFoundException.class,
                () -> userService.getUserByUsername(name)
        );
    }

    @Test
    void returnUserIfPasswordIsCorrectSuccess() {
        //given
        String name = "user";
        String password = userService.getUserByUsername(name).getPassword();

        //when
        User user = userService.returnUserIfPasswordIsCorrect(name, password);

        //them
        Assertions.assertNotNull(user);
        Assertions.assertEquals(name, user.getUsername());
    }

    @Test
    void returnUserIfPasswordIsCorrectThrowsExceptionIfCredentialsNotCorrect() {
        //given
        String name = "user";
        String password = "Wrong password";

        //then
        Assertions.assertThrows(
                UserNotAuthenticatedException.class,
                () -> userService.returnUserIfPasswordIsCorrect(name, password)
        );
        Assertions.assertThrows(
                UserNotAuthenticatedException.class,
                () -> userService.returnUserIfPasswordIsCorrect("Wrong username", password)
        );
    }
}