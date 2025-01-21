package com.ecommerce.demo.exceptionshandlers;

import com.ecommerce.demo.Exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionsHandler {
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> handleProductNotFound(ProductNotFoundException productNotFoundException){
        return new ResponseEntity<>(productNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFound(UserNotFoundException userNotFoundException){
        return new ResponseEntity<>(userNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<String> handleOrderNotFound(OrderNotFoundException orderNotFoundException){
        return new ResponseEntity<>(orderNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(PaymentLinkAlreadyCreatedException.class)
    public ResponseEntity<String> handlePaymentLinkAlreadyCreatedException(PaymentLinkAlreadyCreatedException paymentLinkAlreadyCreatedException){
        return new ResponseEntity<>(paymentLinkAlreadyCreatedException.getMessage(),HttpStatus.CONFLICT);
    }
    @ExceptionHandler(RefundAlreadyCreatedException.class)
    public ResponseEntity<String> handleRefundAlreadyCreatedException(RefundAlreadyCreatedException refundAlreadyCreatedException){
        return new ResponseEntity<>(refundAlreadyCreatedException.getMessage(), HttpStatus.CONTINUE);
    }
    @ExceptionHandler(PaymentLinkNotFoundException.class)
    public ResponseEntity<String> handlePaymentLinkNotFoundException(PaymentLinkNotFoundException paymentLinkNotFoundException){
        return new ResponseEntity<>(paymentLinkNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(RefundNotFoundException.class)
    public ResponseEntity<String> handleRefundNotFoundException(RefundNotFoundException refundNotFoundException){
        return new ResponseEntity<>(refundNotFoundException.getMessage(),HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(PaymentNotCompletedException.class)
    public ResponseEntity<String> handlePaymentNotCompletedFoundException(PaymentNotCompletedException paymentNotCompletedException){
        return new ResponseEntity<>(paymentNotCompletedException.getMessage(), HttpStatus.CONFLICT);
    }
    @ExceptionHandler(ApiLimiterException.class)
    public ResponseEntity<String> handleApiThrottle(ApiLimiterException apiLimiterException){
        return new ResponseEntity<>(apiLimiterException.getMessage(),HttpStatus.TOO_MANY_REQUESTS);
    }

}
