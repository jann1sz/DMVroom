package com.example.demo.TransportComponent.controller;

import com.example.demo.TransportComponent.model.Stop;
import com.example.demo.TransportComponent.model.Transport;
import com.example.demo.TransportComponent.service.ApiMicroservice;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/transport")
public class TransportController {

    private final ApiMicroservice wmataService;
    private final RestTemplate restTemplate = new RestTemplate();

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
    public Transport addPassanger(@PathVariable String transportName) {
        Transport transport = null;

        for (Transport t : currentTransports) {
            if (t.getTransportName().equals(transportName)) {
                transport = t;
            }
        }
        
        if (transport == null) {
            throw new RuntimeException("Transport not found");
        }

        //Calls to the User API to give the Transport Component the users info to be saved as a passenger.
        String userApiUrl = "http://localhost:8080/user/info";
        @SuppressWarnings("unchecked")
        Map<String, Object> userMap = restTemplate.getForObject(userApiUrl, Map.class);

        if (userMap != null) {
            transport.setPassenger(userMap);
            transport.setCapacity(transport.getCapacity() + 1);
        }

        for (Stop stop : currentStops) {
            Transport nextArrival = stop.getNextArrival();
            if (nextArrival != null && nextArrival.getTransportName().equals(transportName)) {
                stop.setNextArrival(transport);
            }
        }

        return transport;
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
