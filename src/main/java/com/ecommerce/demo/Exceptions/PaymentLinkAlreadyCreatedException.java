package com.ecommerce.demo.Exceptions;

public class PaymentLinkAlreadyCreatedException extends Exception{
    public PaymentLinkAlreadyCreatedException(String message){
        super(message);
    }
}
