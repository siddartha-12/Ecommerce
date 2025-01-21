package com.ecommerce.demo.Exceptions;

public class PaymentLinkNotFoundException extends Exception{
    public PaymentLinkNotFoundException(String message){
        super(message);
    }
}
