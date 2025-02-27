package com.kdu.caching.service;

import com.kdu.caching.dto.ApiResponseWrapper;
import com.kdu.caching.dto.ResponseDTO;
import com.kdu.caching.exception.ResourceNotFoundException;
import com.kdu.caching.models.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * The type Geocode service.
 */
@Service
@RequiredArgsConstructor
public class GeoCodeService {
    private static final Logger logger = Logger.getLogger(GeoCodeService.class.getName());
    private final RestTemplate restTemplate;
    private final ResponseDTO responseDTO;


    @Value("${api-key}")
    private String apiKey;

    private String forwardApiRequest(String location){
       return "https://api.positionstack.com/v1/forward?access_key="+apiKey+ "&query="+location;
    }

    private Map<String,Double> getGeoCodeFromApi(String address){

        String url= forwardApiRequest(address);

        ApiResponseWrapper apiResponseWrapper = restTemplate.getForObject(url, ApiResponseWrapper.class);


        if (apiResponseWrapper == null || apiResponseWrapper.getData() == null || apiResponseWrapper.getData().isEmpty()) {
            throw new ResourceNotFoundException("Coordinates not found for address: " + address);
        }

        ApiResponse apiResponse = apiResponseWrapper.getData().get(0);
        logger.log(Level.INFO,"The coordinates has been fetched for the address {0} from the api", address);
        return responseDTO.apiResponseMapper(apiResponse);
    }

    /**
     * Get geoCode map.
     *
     * @param address the address
     * @return the map
     */
    @Cacheable(
            value = "geocoding",
            key = "#address",
            condition = "#address != 'goa'"
    )
    public Map<String,Double> getGeoCode(String address){

        return getGeoCodeFromApi(address);
    }
}
