package com.example.demo.UserComponent.service;

import com.example.demo.UserComponent.model.Preferences;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

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
    public void updatePreferences(String username, Preferences updatedPreferences){
        preferencesStorage.put(username, updatedPreferences);
    }
}
