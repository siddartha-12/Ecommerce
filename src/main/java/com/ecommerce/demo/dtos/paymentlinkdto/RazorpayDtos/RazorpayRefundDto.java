package com.ecommerce.demo.dtos.paymentlinkdto.RazorpayDtos;


public class RazorpayRefundDto {
    private final String refundId;
    private final String refundStatus;

    public String getRefundId() {
        return refundId;
    }

    public String getRefundStatus() {
        return refundStatus;
    }

    public RazorpayRefundDto(String refundId, String refundStatus) {
        this.refundId = refundId;
        this.refundStatus = refundStatus;
    }
}
