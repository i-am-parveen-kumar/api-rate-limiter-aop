package com.product.apiratelimiter.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.product.apiratelimiter.DTO.APIRateDTO;

@Mapper
public interface APIMapper {

    @Select("select 1 from apikeys where apiKey =#{apiKey}")
    Integer isApiKeyValid(@Param("apiKey") String apiKey);

    @Select("select apiHitSeconds,apiHitRate,akrel.apiKeyRateEndPointId from apiendpoints aep \n"
            + "inner join apikeyrateendpointlink akrel on akrel.apiEndPointId = aep.apiEndPointId\n"
            + "inner join apikeys ak on ak.apiKeyId = akrel.apiKeyId\n"
            + "inner join apiratesettings ars on ars.apiRateSettingId = akrel.apiRateId\n"
            + "where aep.endPointName =#{endPoint}")
    APIRateDTO getApiRateData(@Param("endPoint") String endPoint);

    @Select("select count(*) from apiHitReceived where apiKeyRateEndPointId =#{apiKeyRateEndPointId}"
            + "  AND receivedOn > (now() - interval  ${seconds} second) AND receivedOn < now()")
    Integer getApiRate(@Param("seconds") Integer seconds, Long apiKeyRateEndPointId);

    @Insert("insert into apiHitReceived(apiKeyRateEndPointId,receivedOn) "
            + "values (#{apiKeyRateEndPointId},now())")
    void insertApiHit(@Param("apiKeyRateEndPointId") Long apiKeyRateEndPointId);
}
