package com.ecommerce.demo.PaymentGateways;

import com.ecommerce.demo.Entities.Refund;
import com.ecommerce.demo.Entities.User;
import com.ecommerce.demo.Exceptions.PaymentNotCompletedException;
import com.ecommerce.demo.dtos.paymentlinkdto.RazorpayDtos.PaymentLink;
import com.ecommerce.demo.dtos.paymentlinkdto.RazorpayDtos.RazorpayPaymentLink;

import java.util.List;
import java.util.Map;

public interface PaymentGateway {
    public PaymentLink generatePaymentLink(double amount, String currency,Long orderId,
                                           Map<String, String> notes, String description);

    public PaymentLink getPaymentLink(String id);

    public String refundForPaymentLink(String id) throws PaymentNotCompletedException;
}
