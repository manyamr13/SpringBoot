package com.example.locationtracker;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/location")
public class LocationController {

    @PostMapping
    public ResponseEntity<String> receiveLocation(@RequestBody LocationRequest location) {
        System.out.println("Received location: " + location);
        return ResponseEntity.ok("Location received: " + location);
    }

    public static class LocationRequest {
        private double latitude;
        private double longitude;

        public double getLatitude() { return latitude; }
        public void setLatitude(double latitude) { this.latitude = latitude; }
        public double getLongitude() { return longitude; }
        public void setLongitude(double longitude) { this.longitude = longitude; }

        @Override
        public String toString() {
            return "Latitude: " + latitude + ", Longitude: " + longitude;
        }
    }
}
