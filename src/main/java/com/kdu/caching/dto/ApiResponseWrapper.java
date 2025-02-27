package com.kdu.caching.dto;


import com.kdu.caching.models.ApiResponse;
import lombok.Data;
import java.util.List;

/**
 * The type Api response wrapper.
 */
@Data
public class ApiResponseWrapper {

    private List<ApiResponse> data;

}

