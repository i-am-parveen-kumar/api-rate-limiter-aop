package com.product.apiratelimiter.Service;

import com.product.apiratelimiter.exceptions.APIException;

public interface ApiService {

    void validateRequest(String apiKey, String apiEndPoint) throws APIException;

}
