package com.product.apiratelimiter.exceptions;

       @SuppressWarnings("serial")
    public class APIException extends Exception {
        private int status;
        public APIException(int status, String message) {
            super(message);
            this.status = status;

        }
        public APIException(Throwable e) {
            super(e);
            this.status = 0;

        }
        public int getStatus() {
            return status;
        }

    }
