package com.ecommerce.demo.services.apilimiter;

public interface ApiLimiterService {
    public boolean isRequestAllowed(Long userId);
}
