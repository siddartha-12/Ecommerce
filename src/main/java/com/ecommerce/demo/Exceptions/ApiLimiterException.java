package com.ecommerce.demo.Exceptions;

public class ApiLimiterException extends Exception{
    public ApiLimiterException(String message){
        super(message);
    }
}
