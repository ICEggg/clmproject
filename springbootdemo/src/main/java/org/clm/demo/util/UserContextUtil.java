package org.clm.demo.util;

import org.clm.demo.mvc.primiary.entity.User;

public class UserContextUtil {
    private static ThreadLocal<User> userHolder = new ThreadLocal<User>();

    public static void setUser(User user) {
        userHolder.set(user);
    }

    public static User getUser() {
        return userHolder.get();
    }

}
