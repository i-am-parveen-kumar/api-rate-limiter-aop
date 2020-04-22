package com.product.apiratelimiter.controller;

import java.util.Collections;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.product.apiratelimiter.model.PostResponse;

@RestController
public class ApiController {

    @RequestMapping("/test")
    PostResponse test() {
        return new PostResponse(1, "hey", Collections.EMPTY_LIST);
    }

}
