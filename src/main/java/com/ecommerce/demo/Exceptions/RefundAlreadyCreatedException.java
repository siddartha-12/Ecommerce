package com.ecommerce.demo.Exceptions;

public class RefundAlreadyCreatedException extends Exception{
    public RefundAlreadyCreatedException(String message){
        super(message);
    }
}
