package com.kdu.caching.dto;

import com.kdu.caching.models.ApiResponse;
import com.kdu.caching.models.GeoCode;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Response dto.
 */
@Component
public class ResponseDTO {
    /**
     * Geo code mapper map.
     *
     * @param geoCode the geoCode
     * @return the map
     */
    public Map<String,Double> geoCodeMapper(GeoCode geoCode){
        HashMap<String,Double> geoCodeMap = new HashMap<>();
        geoCodeMap.put("latitude",geoCode.getLatitude());
        geoCodeMap.put("longitude",geoCode.getLongitude());
        return geoCodeMap;
    }

    /**
     * Api response mapper map.
     *
     * @param apiResponse the api response
     * @return the map
     */
    public Map<String,Double> apiResponseMapper(ApiResponse apiResponse){
        HashMap<String,Double> geoCodeMap = new HashMap<>();
        geoCodeMap.put("latitude",apiResponse.getLatitude());
        geoCodeMap.put("longitude",apiResponse.getLongitude());
        return geoCodeMap;
    }
}
