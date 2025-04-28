package com.example.demo.UserComponent.model;

import java.util.List;

public class Preferences {
    
    private String username;
    private String modeOfTransportation;
    private String preferredCrowdedness;
    private List<String> favoriteStops;

    public Preferences(String username){
        this.username = username;
        this.modeOfTransportation = "";
        this.preferredCrowdedness = "";
        this.favoriteStops = null;

    }

    //getter methods
    public String getUsername(){ return username; }
    public String getModeOfTransportation(){ return modeOfTransportation; }
    public String getPreferredCrowdedness(){ return preferredCrowdedness; }
    public List<String> getFavoriteStops(){ return favoriteStops; }

    //setter methods
    public void setUsername(String username){ this.username = username; }
    public void setModeOfTransportation(String modeOfTransportation){ this.modeOfTransportation = modeOfTransportation; }
    public void setPreferredCrowdedness(String preferredCrowdedness){ this.preferredCrowdedness = preferredCrowdedness; }
    public void setFavoriteStops(List<String> favoriteStops){ this.favoriteStops = favoriteStops; }

    
}
