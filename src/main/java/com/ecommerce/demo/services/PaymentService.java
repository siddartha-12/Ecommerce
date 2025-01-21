package com.ecommerce.demo.services;

import com.ecommerce.demo.Entities.CustomerOrder;
import com.ecommerce.demo.Entities.Product;
import com.ecommerce.demo.Entities.Refund;
import com.ecommerce.demo.Entities.User;
import com.ecommerce.demo.Exceptions.*;
import com.ecommerce.demo.PaymentGateways.PaymentGateway;
import com.ecommerce.demo.dtos.paymentlinkdto.RazorpayDtos.PaymentLink;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PaymentService {

    private PaymentGateway paymentGateway;
    private OrderService orderService;
    private UserService userService;
    private String currency;
    public PaymentService(PaymentGateway paymentGateway, OrderService orderService, UserService userService){
        this.paymentGateway = paymentGateway;
        this.orderService = orderService;
        this.userService = userService;
        this.currency = "INR";
    }
    public PaymentLink makePaymentForOrder(Long orderId) throws OrderNotFoundException, UserNotFoundException, PaymentLinkAlreadyCreatedException {
        PaymentLink paymentLink = checkPaymentLinkAlreadyExistAndReturn(orderId);
        if(paymentLink!=null){
            throw new PaymentLinkAlreadyCreatedException(paymentLink.getId());
        }
        CustomerOrder order = orderService.getOrderById(orderId);
        User user = userService.getUserById(order.getUser().getId());
        Map<String, String> notes = new HashMap<>();
        for(Product product:order.getInvoice().getProducts()){
            notes.put(product.getTitle(),Double.toString(product.getPrice()));
        }
        paymentLink = paymentGateway.generatePaymentLink(order.getInvoice().getTotalAmount(),
                this.currency,orderId,notes, "Test Payment");
        order.setPaymentLink(paymentLink);
        orderService.updateOrder(order);
        return paymentLink;
    }
    private PaymentLink checkPaymentLinkAlreadyExistAndReturn(Long orderId){
        try {
            if(orderService.isPaymentLinkAlreadyCreated(orderId)){
                CustomerOrder order = orderService.getOrderById(orderId);
                return order.getPaymentLink();
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
        return null;

    }
    private Refund checkIfRefundAlreadyCreated(Long id){
        try {
            if (orderService.isRefundAlreadyCreated(id)) {
                Refund refund = orderService.getOrderById(id).getRefund();
                return refund;
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
        return null;
    }
    public Refund createRefundForOrder(Long id) throws RefundAlreadyCreatedException, OrderNotFoundException, PaymentLinkNotFoundException, PaymentNotCompletedException {
        Refund refund = checkIfRefundAlreadyCreated(id);
        if(refund!=null){
            throw new RefundAlreadyCreatedException("Refund already created "+refund.getId());
        }
        String refundId = null;

        CustomerOrder order = orderService.getOrderById(id);
        PaymentLink paymentLink = order.getPaymentLink();
        if(paymentLink==null){
            throw new PaymentLinkNotFoundException("Payment not found for this order");
        }
        refundId = paymentGateway.refundForPaymentLink(paymentLink.getId());
        refund = new Refund("CREATED",refundId);
        order.setRefund(refund);
        orderService.updateOrder(order);

        return refund;
    }



}
