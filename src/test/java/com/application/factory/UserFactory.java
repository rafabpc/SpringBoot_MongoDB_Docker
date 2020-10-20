package com.application.factory;

import com.application.model.User;

import java.util.LinkedList;
import java.util.List;

public class UserFactory {

    public static User getGeneratedUser(){
        User user = new User();
        user.setUserName("Test User");
        return user;
    }

    public static List<User> getGeneratedList(){
        List<User> users = new LinkedList<>();
        users.add(getGeneratedUser());
        return users;
    }

}
