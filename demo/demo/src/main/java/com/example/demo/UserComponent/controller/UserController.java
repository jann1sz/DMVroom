// UserController.java
package com.example.demo.UserComponent.controller;

import com.example.demo.UserComponent.model.User;
import com.example.demo.UserComponent.service.UserService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    
    private final UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }

    //This is where the transport Controller gets user info, currently it's set up to use whatever is in UserService.java but so long as you give me a user object I can parse it
    @GetMapping("/current")
    public User getCurrentUser() {
        return userService.getCurrentUser();
    }
}

