package com.example.gmaps;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MapController {

    @GetMapping("/")
    public ModelAndView showMap() {
        return new ModelAndView("map");
    }
}
