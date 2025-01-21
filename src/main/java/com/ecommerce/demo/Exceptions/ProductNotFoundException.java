package com.ecommerce.demo.Exceptions;

public class ProductNotFoundException extends Exception{
    public ProductNotFoundException(Long id){
        super("Product not found "+id);
    }
}
