package com.example.demo.UserComponent.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.demo.UserComponent.model.Preferences;

@Service
public class PreferencesService {
    
    private final Map<String, Preferences> preferencesStorage = new HashMap<>();

    
    /*
     * Function takes the key (username) and retries the value stored at the key (preferences),
     * otherwise, it creates a new set of preferences.
     */
    public Preferences getOrCreatePreferences(String username) {
        return preferencesStorage.computeIfAbsent(username, a -> new Preferences(a));
    }

    /**
     * Updates the preference set at the key [username]
     */
    public void updatePreferences(String username, Preferences updatedPreferences) {
        Preferences existingPreferences = getOrCreatePreferences(username);
    
        if (updatedPreferences.getModeOfTransportation() != null) {
            existingPreferences.setModeOfTransportation(updatedPreferences.getModeOfTransportation());
        }
    
        if (updatedPreferences.getPreferredCrowdedness() != null) {
            existingPreferences.setPreferredCrowdedness(updatedPreferences.getPreferredCrowdedness());
        }
    
        if (updatedPreferences.getFavoriteStops() != null && !updatedPreferences.getFavoriteStops().isEmpty()) {
            existingPreferences.setFavoriteStops(updatedPreferences.getFavoriteStops());
        }
    
        preferencesStorage.put(username, existingPreferences);
    }
    

    public List<Preferences> getAllUsers() {
        return new ArrayList<>(preferencesStorage.values());
    }
}
