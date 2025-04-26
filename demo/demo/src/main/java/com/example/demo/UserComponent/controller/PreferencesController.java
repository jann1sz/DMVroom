package com.example.demo.UserComponent.controller;
import com.example.demo.UserComponent.model.Preferences;
import com.example.demo.UserComponent.service.PreferencesService;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/preferences")
public class PreferencesController {
    

    public final PreferencesService preferencesService;

    public PreferencesController(PreferencesService preferencesService){
        this.preferencesService = preferencesService;
    }

    /**
     * Showing the preferences for the user that is logged in
     * @param username
     * @param model
     * @param session
     * @return
     */
    @GetMapping("/{username}")
    public String showPreferences(@PathVariable String username, Model model, HttpSession session){
        Preferences p = preferencesService.getOrCreatePreferences(username);

        model.addAttribute("username", username);
        model.addAttribute("preferences", p);

        return "user/preferences";
    }

    /**
     * Updating preferences
     * @param username
     * @param updatePreferences
     * @return
     */
    @PostMapping("/{username}")
    public String updatePreferences(@PathVariable String username, @ModelAttribute Preferences updatePreferences){
        preferencesService.updatePreferences(username, updatePreferences);
        return "redirect:/preferences/" + username;
    }


}
