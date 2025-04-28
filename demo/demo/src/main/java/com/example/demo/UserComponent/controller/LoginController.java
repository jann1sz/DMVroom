package com.example.demo.UserComponent.controller;


import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.example.demo.UserComponent.service.PreferencesService;
/**
 * Controller
 */
@Controller
public class LoginController {

    private final PreferencesService preferencesService;

    public LoginController (PreferencesService preferencesService){
        this.preferencesService = preferencesService;
    }
    
    @GetMapping("/login")
    public String showLoginPage(){
        return "user/login";
    }

    @PostMapping("/login")
    public String handleLogin(@RequestParam String username, HttpSession session){

        boolean exists = preferencesService.getAllUsers().stream()
            .anyMatch(ps -> ps.getUsername().equals(username));

        if(!exists){
            preferencesService.getOrCreatePreferences(username);
        }

        session.setAttribute("username", username);
        return "redirect:/preferences/" + username;
    }

    @PostMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/login";
    }


}


