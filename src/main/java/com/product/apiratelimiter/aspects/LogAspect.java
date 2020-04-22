package com.product.apiratelimiter.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import com.product.apiratelimiter.model.PostResponse;

@Aspect
@Configuration
public class LogAspect {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* com.product.apiratelimiter.controller.*.*(..))")
    public void logMethods() {
    };

    @Before("logMethods()")
    public void logMethodsController(JoinPoint jp) {
        logger.info("{}", jp);
    }

    @AfterReturning(value = "logMethods()", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        try {
            PostResponse res = (PostResponse) result;
            if (res != null) {
                logger.info("{} returned with STATUS---> {}  message ----> {}" + joinPoint,
                        res.getStatus(), res.getMessage());
            }
        } catch (ClassCastException cce) {
        }
    }
}
