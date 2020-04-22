package com.product.apiratelimiter.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.product.apiratelimiter.DTO.APIRateDTO;
import com.product.apiratelimiter.Service.ApiService;
import com.product.apiratelimiter.exceptions.APIException;
import com.product.apiratelimiter.mapper.APIMapper;

@Service
public class ApiServiceImpl implements ApiService {
    @Autowired
    private APIMapper apiMapper;

    @Override
    public void validateRequest(String apiKey, String apiEndPoint) throws APIException {
        Integer isApiKeyValid = apiMapper.isApiKeyValid(apiKey);
        if (isApiKeyValid == null || !isApiKeyValid.equals(1)) {
            throw new APIException(0, "API Credentials not valid");
        }

        APIRateDTO apiData = apiMapper.getApiRateData(apiEndPoint);
        if (apiData == null)
            throw new APIException(0, "API Settings not found");
        Integer apiRate = apiMapper.getApiRate(apiData.getApiHitSeconds(),
                apiData.getApiKeyRateEndPointId());

        if (apiRate >= apiData.getApiHitRate())
            throw new APIException(0, "API Hit Quota Exceeded");
        apiMapper.insertApiHit(apiData.getApiKeyRateEndPointId());
    }
}
