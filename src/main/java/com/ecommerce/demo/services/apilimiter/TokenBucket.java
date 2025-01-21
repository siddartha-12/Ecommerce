package com.ecommerce.demo.services.apilimiter;

import com.ecommerce.demo.interfaces.ApiLimiterAlgorithm;

public class TokenBucket implements ApiLimiterAlgorithm {
   private final Integer maxRequests = 2;
   private final Integer refillRateForSecond = 3;
   public TokenBucket(){
      this.availableTokens = maxRequests;
      this.lastFilledTime = System.currentTimeMillis();
   }
   private Integer availableTokens;
   private Long lastFilledTime;


   @Override
   public boolean isRequestAllowed() {
      refill();
      if(this.availableTokens>0){
         this.availableTokens-=1;
         return true;
      }
      return false;
   }
   private void refill(){
      Integer totalRefillsCount = Math.toIntExact((System.currentTimeMillis() - this.lastFilledTime) / 1000);
      this.availableTokens = Math.min(maxRequests,this.availableTokens+totalRefillsCount*refillRateForSecond);
      this.lastFilledTime = System.currentTimeMillis();
   }
}
