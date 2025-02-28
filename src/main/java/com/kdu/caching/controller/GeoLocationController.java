package com.kdu.caching.controller;

import com.kdu.caching.service.GeoCodeService;
import com.kdu.caching.service.ReverseGeoCodeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * The type Geo location controller.
 */
@RestController
@AllArgsConstructor
public class GeoLocationController {
    private final GeoCodeService geoCodeService;
    private final ReverseGeoCodeService reverseGeoCodeService;

    /**
     * Get geo response entity.
     *
     * @param address the address
     * @return the response entity
     */
    @GetMapping("/geocoding")
    public ResponseEntity<Map<String,Double>> getGeo(@RequestParam @NotBlank String address){
        Map<String,Double> geoCode= geoCodeService.getGeoCode(address);
        return new ResponseEntity<>(geoCode, HttpStatus.OK);
    }

    @GetMapping("/health-check")
    public String healthCheck(){
        return "Service is up and running";
    }

    /**
     * Get reverse geo response entity.
     *
     * @param latitude  the latitude
     * @param longitude the longitude
     * @return the response entity
     */
    @GetMapping("/reverse-geocoding")
    public ResponseEntity<String> getReverseGeo(@RequestParam @NotNull Double latitude,@RequestParam @NotNull Double longitude){
        String reverseGeoCode = reverseGeoCodeService.getReverseGeoCode(latitude, longitude);
        return new ResponseEntity<>(reverseGeoCode, HttpStatus.OK);
    }
}
