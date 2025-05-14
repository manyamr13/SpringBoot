package com.example.gmaps;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class MapController {

    @GetMapping("/")
    public ModelAndView showMap() {
        return new ModelAndView("map");
    }
}
