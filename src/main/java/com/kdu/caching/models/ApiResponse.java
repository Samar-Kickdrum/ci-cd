package com.kdu.caching.models;

import lombok.Data;

/**
 * The type Api response.
 */
@Data
public class ApiResponse {
    private double latitude;
    private double longitude;
    private String label;
    private String name;
    private String type;
    private String number;
    private String street;
    private String postal_code;
    private int confidence;
    private String region;
    private String region_code;
    private Object administrative_area;
    private String neighbourhood;
    private String country;
    private String country_code;
    private String map_url;
}
