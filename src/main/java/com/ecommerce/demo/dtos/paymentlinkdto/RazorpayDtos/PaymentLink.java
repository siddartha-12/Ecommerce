package com.ecommerce.demo.dtos.paymentlinkdto.RazorpayDtos;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class PaymentLink {
    @Id
    protected String id;

    public PaymentLink() {
    }

    public String getId() {
        return id;
    }

    protected String shortUrl;
    protected String status;

    public String getShortUrl() {
        return shortUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public PaymentLink(String id, String shortUrl, String status) {
        this.id=id;
        this.shortUrl = shortUrl;
        this.status = status;
    }

}
