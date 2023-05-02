package com.abdddev.restaurantvoiting.web.user;

import com.abdddev.restaurantvoiting.model.user.Role;
import com.abdddev.restaurantvoiting.model.user.User;
import com.abdddev.restaurantvoiting.util.JsonUtil;
import com.abdddev.restaurantvoiting.web.MatcherFactory;

import java.util.Collections;
import java.util.Date;
import java.util.List;

public class UserTestData {
    public static final MatcherFactory.Matcher<User> USER_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(User.class, "enabled", "registered", "votes", "password");

    public static final int USER_ID = 1;
    public static final int ADMIN_ID = 2;
    public static final int GUEST_ID = 7;
    public static final int NOT_FOUND = 100;
    public static final String USER_MAIL = "user@yandex.ru";
    public static final String ADMIN_MAIL = "admin@gmail.com";
    public static final String GUEST_MAIL = "guest@gmail.com";
    public static final String USER2_MAIL = "user2@yandex.ru";

    public static final User user = new User(USER_ID, "User", USER_MAIL, "user", Role.USER);
    public static final User admin = new User(ADMIN_ID, "Admin", ADMIN_MAIL, "admin", Role.ADMIN, Role.USER);
    public static final User guest = new User(GUEST_ID, "Guest", GUEST_MAIL, "guest");
    public static final User user2 = new User(USER_ID + 2, "User2", USER2_MAIL, "user2", Role.USER);
    public static final User user3 = new User(USER_ID + 3, "User3", "user3@yandex.ru", "user3", Role.USER);
    public static final User user4 = new User(USER_ID + 4, "User4", "user4@yandex.ru", "user4", Role.USER);
    public static final User user5 = new User(USER_ID + 5, "User5", "user5@yandex.ru", "user5", Role.USER);

    public static User getNew() {
        return new User(null, "New", "new@gmail.com", "newPass", false, new Date(), Collections.singleton(Role.USER));
    }

    public static User getUpdated() {
        return new User(USER_ID, "UpdatedName", USER_MAIL, "newPass", false, new Date(), List.of(Role.ADMIN));
    }

    public static String jsonWithPassword(User user, String passw) {
        return JsonUtil.writeAdditionProps(user, "password", passw);
    }
}
