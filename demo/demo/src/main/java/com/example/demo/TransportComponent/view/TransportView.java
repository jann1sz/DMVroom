package com.example.demo.TransportComponent.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/transport")
public class TransportView {
    @GetMapping("/view")
    public String showMapPage() {
        return "transport/map";
    }
}
