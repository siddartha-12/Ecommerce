package com.ecommerce.demo.services.apilimiter;

import com.ecommerce.demo.interfaces.ApiLimiterAlgorithm;

public class LeakyBucket implements ApiLimiterAlgorithm {
    private final Integer maxRequests = 1;
    private final Integer reFillingRateForSecond = 2;
    public LeakyBucket(){
        this.lastFilledTime = System.currentTimeMillis();
        this.currentWater = maxRequests;
    }

    private Integer currentWater;
    private Long lastFilledTime;


    @Override
    public boolean isRequestAllowed() {
        leak();
        if(this.currentWater<maxRequests){
            this.currentWater+=1;
            return true;
        }
        return false;
    }
    private void leak(){
        Integer totalRefillsCount = Math.toIntExact((System.currentTimeMillis() - this.lastFilledTime)/1000);
        this.currentWater=Math.max(maxRequests,this.currentWater-(totalRefillsCount*reFillingRateForSecond));
        this.lastFilledTime = System.currentTimeMillis();
    }
}
