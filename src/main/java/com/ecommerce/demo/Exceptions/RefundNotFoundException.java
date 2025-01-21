package com.ecommerce.demo.Exceptions;

public class RefundNotFoundException extends Exception{
    public RefundNotFoundException(String message){
        super(message);
    }
}
