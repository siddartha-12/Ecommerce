package com.ecommerce.demo.Exceptions;

public class PaymentNotCompletedException extends Exception{
    public PaymentNotCompletedException(String message){
        super(message);
    }
}
