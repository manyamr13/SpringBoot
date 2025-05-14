package com.example.gmaps;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MapController {

    @GetMapping("/")
    public String home() {
        return "map"; // loads src/main/resources/templates/map.html
    }
}
