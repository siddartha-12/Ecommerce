package com.ecommerce.demo.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Refund {
    public Refund(String status, String id) {
        this.status = status;
        this.id = id;
    }

    private String status;

    public String getId() {
        return id;
    }

    public Refund() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    @Id
    private String id;
}
