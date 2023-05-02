package com.abdddev.restaurantvoiting.util;

import com.abdddev.restaurantvoiting.model.user.Role;
import com.abdddev.restaurantvoiting.model.user.User;
import com.abdddev.restaurantvoiting.to.UserTo;
import lombok.experimental.UtilityClass;

import static com.abdddev.restaurantvoiting.config.WebSecurityConfig.PASSWORD_ENCODER;

@UtilityClass
public class UsersUtil {
    public static User createNewFromTo(UserTo userTo) {
        return new User(null, userTo.getName(), userTo.getEmail().toLowerCase(), userTo.getPassword(), Role.USER);
    }

    public static User updateFromTo(User user, UserTo userTo) {
        user.setName(userTo.getName());
        user.setEmail(userTo.getEmail().toLowerCase());
        user.setPassword(userTo.getPassword());
        return user;
    }

    public static User prepareToSave(User user) {
        user.setPassword(PASSWORD_ENCODER.encode(user.getPassword()));
        user.setEmail(user.getEmail().toLowerCase());
        return user;
    }
}