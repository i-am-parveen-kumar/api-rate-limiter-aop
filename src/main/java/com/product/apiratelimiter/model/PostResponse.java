package com.product.apiratelimiter.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostResponse {

    private int status;
    private String message;
    private Object data;

    public PostResponse(int status, String message, Object data) {
        super();
        this.status = status;
        this.message = message;
        this.data = data;

    }
}
