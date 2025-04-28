package com.example.demo.TransportComponent.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.TransportComponent.model.Stop;
import com.example.demo.TransportComponent.model.Transport;
import com.example.demo.TransportComponent.service.ApiMicroservice;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/transport")
public class TransportController {

    private final ApiMicroservice wmataService;
    private List<Transport> currentTransports;
    private List<Stop> currentStops;

    //Constructor
    public TransportController(ApiMicroservice wmataService) {
        this.wmataService = wmataService;
        this.currentTransports = wmataService.parseTransports();
        this.currentStops = wmataService.parseStops();
    }

    //Storage for this list, without these the api would be called multiple times and be incredibly slow. Now its only incredibly slow at first.
    @ResponseBody
    @GetMapping("/transportList")
    public List<Transport> getTransports() {
        return currentTransports;
    }

    @ResponseBody
    @GetMapping("/stopList")
    public List<Stop> getStops() {
        return currentStops;
    }

    //Finds the specific transported in the list of all transports then adds a User to the 'Passenger' map.
    @PostMapping("/{transportName}/setPassenger")
    public void addPassenger(@PathVariable String transportName, HttpSession session) {
        Transport transport = null;

        for (Transport t : currentTransports) {
            if (t.getTransportName().equals(transportName)) {
                transport = t;
            }
        }
        
        if (transport == null) {
            throw new RuntimeException("Transport not found");
        }

        //Calls to the Preferences API to give the Transport Component the users info to be saved as a passenger.
        String user = (String) session.getAttribute("username");

        System.out.println(user);
        if (user != null) {
            transport.setPassenger(user);
            transport.setCapacity(transport.getCapacity() + 1);
        }

        for (Stop stop : currentStops) {
            Transport nextArrival = stop.getNextArrival();
            if (nextArrival != null && nextArrival.getTransportName().equals(transportName)) {
                stop.setNextArrival(transport);
            }
        }
    }

    //Gets a unique Stop so long as the user has a Stops stopName, for use by User Components favorite stops.
    @ResponseBody
    @GetMapping("/stop/{stopName}")
    public Stop getStopByName(String stopName) {
        for (Stop stop : currentStops) {
            if (stop.getStopName().equalsIgnoreCase(stopName)) {
                return stop;
            }
        }
        throw new RuntimeException("Stop not found: " + stopName);
    }

}
