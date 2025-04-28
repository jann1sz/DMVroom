package com.example.demo.UserComponent.controller;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.example.demo.TransportComponent.model.Stop;
import com.example.demo.UserComponent.model.Preferences;
import com.example.demo.UserComponent.service.PreferencesService;

import jakarta.servlet.http.HttpSession;



@Controller
@RequestMapping("/preferences")
public class PreferencesController {
    

    public final PreferencesService preferencesService;
    private final RestTemplate restTemplate = new RestTemplate();

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

        Stop[] stopsArray = restTemplate.getForObject("http://localhost:8080/transport/stopList", Stop[].class);
        List<Stop> stopList = Arrays.asList(stopsArray);
        model.addAttribute("stopList", stopList);

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

    @GetMapping
    public List<Preferences> showAllPreferences (Model model){
        return preferencesService.getAllUsers();
    }

    @GetMapping("/info")
    @ResponseBody
    public String getUserInfo(HttpSession session) {
        return (String) session.getAttribute("username");
    }
}
