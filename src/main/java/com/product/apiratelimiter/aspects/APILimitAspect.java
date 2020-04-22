package com.product.apiratelimiter.aspects;

import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.product.apiratelimiter.Service.ApiService;
import com.product.apiratelimiter.model.PostResponse;

@Aspect
@Configuration
public class APILimitAspect {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ApiService apiService;

    @Around("@annotation(requestMapping)")
    public Object around(ProceedingJoinPoint pjp, RequestMapping requestMapping) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        String apikey = request.getHeader("apikey");
        String endPoint = request.getRequestURI();
        logger.info("{}", pjp);
        try {
            apiService.validateRequest(apikey, endPoint);
        } catch (Exception e) {
            return new PostResponse(0, e.getMessage(), Collections.EMPTY_LIST);
        }
        return pjp.proceed();
    }
}
