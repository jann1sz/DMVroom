package com.example.demo.UserComponent.service;

import com.example.demo.UserComponent.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    //For Testing Purposes REMOVE IN FINAL VERSION
    public User getCurrentUser() {
        return new User("DemoUser", "demo@example.com");
    }
}
