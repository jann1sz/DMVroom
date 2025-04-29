package com.example.demo.UserComponent.model;
import java.util.*;
import com.example.demo.TransportComponent.model.Stop;

public class StopsManage {

    private List<Stop> stops;

    public List<Stop> newStops(List<Stop> stops){
        this.stops = new ArrayList<>(stops);
        return this.stops;
    }

    public void addStops(List<Stop> newStops){
        this.stops.addAll(newStops);
    }

    public void removeStops(List<Stop> stopsRemove){
        this.stops.removeAll(stopsRemove);
    }

    public void setStops(List<Stop>stops){
        this.stops = stops;
    }

    public List<Stop> getStops(){
        return stops;
    }
}
