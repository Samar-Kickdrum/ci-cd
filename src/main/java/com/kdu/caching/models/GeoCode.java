package com.kdu.caching.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Geo code.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeoCode {
    private String label;
    private double latitude;
    private double longitude;
    private String mapUrl;
    private String postalCode;
    private String country;
}
