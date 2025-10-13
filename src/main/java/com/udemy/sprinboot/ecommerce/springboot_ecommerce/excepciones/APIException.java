package com.udemy.sprinboot.ecommerce.springboot_ecommerce.excepciones;

public class APIException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public APIException() {
    }

    public APIException(String message) {
        super(message);
    }
}
