package com.kdu.caching.service;

import com.kdu.caching.dto.ApiResponseWrapper;
import com.kdu.caching.exception.ResourceNotFoundException;
import com.kdu.caching.models.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The type Reverse geoCode service.
 */
@Service
@RequiredArgsConstructor
public class ReverseGeoCodeService {
    private static final Logger logger = Logger.getLogger(ReverseGeoCodeService.class.getName());
    private final RestTemplate restTemplate;

    @Value("${api-key}")
    private String apiKey;

    private String reverseApiRequest(Double latitude,Double longitude){
        return "https://api.positionstack.com/v1/reverse?access_key="+apiKey+ "&query="+latitude+","+longitude;
    }

    private String getReverseGeoCodeFromApi(Double latitude, Double longitude){
        String url= reverseApiRequest(latitude,longitude);

        ApiResponseWrapper apiResponseWrapper = restTemplate.getForObject(url, ApiResponseWrapper.class);


        if (apiResponseWrapper == null || apiResponseWrapper.getData() == null || apiResponseWrapper.getData().isEmpty()) {
           throw new ResourceNotFoundException("Invalid coordinates has been provided: "+latitude+","+longitude);
        }

        ApiResponse apiResponse = apiResponseWrapper.getData().get(0);
        logger.log(Level.INFO,"The address has been fetched for the coordinates {0},{1} from the api", new Double[]{latitude, longitude});
        return apiResponse.getLabel();
    }

    /**
     * Get reverse-geoCode string.
     *
     * @param latitude  the latitude
     * @param longitude the longitude
     * @return the string
     */
    @Cacheable(
            value = "reverse-geocoding",
            key = "T(java.util.Arrays).asList(#latitude, #longitude)",
            unless = "#result == null"
    )
    public String getReverseGeoCode(Double latitude,Double longitude){
        logger.log(Level.INFO,"The address has been fetched for the coordinates {0},{1} from the cache memory", new Double[]{latitude, longitude});
        return getReverseGeoCodeFromApi(latitude, longitude);
    }
}
