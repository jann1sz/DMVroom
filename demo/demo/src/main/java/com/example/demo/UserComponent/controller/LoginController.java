package com.example.demo.UserComponent.controller;


import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
 
/**
 * Controller
 */
@Controller
public class LoginController {
    
    @GetMapping("/login")
    public String showLoginPage(){
        return "user/login";
    }

    @PostMapping("/login")
    public String handleLogin(@RequestParam String username, HttpSession session){
        session.setAttribute("username", username);
        return "redirect:/preferences/" + username;
    }
}


