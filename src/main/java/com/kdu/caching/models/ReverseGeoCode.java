package com.kdu.caching.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Reverse geoCode.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReverseGeoCode {
    private String label;
    private double latitude;
    private double longitude;
    private String mapUrl;
    private String postalCode;
    private String country;
}
