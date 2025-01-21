package com.ecommerce.demo.Exceptions;

public class OrderNotFoundException extends Exception{
    public OrderNotFoundException(String message){
        super(message);
    }
}
