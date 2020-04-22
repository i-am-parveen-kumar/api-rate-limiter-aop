package com.product.apiratelimiter.DTO;

import lombok.Data;

@Data
public class APIRateDTO {
    private Integer apiHitSeconds;
    private Integer apiHitRate;
    private Long apiKeyRateEndPointId;
}
