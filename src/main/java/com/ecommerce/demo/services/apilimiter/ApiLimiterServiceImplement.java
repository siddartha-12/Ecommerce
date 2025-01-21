package com.ecommerce.demo.services.apilimiter;

import com.ecommerce.demo.interfaces.ApiLimiterAlgorithm;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ApiLimiterServiceImplement implements ApiLimiterService{
    private final Map<Long, ApiLimiterAlgorithm> apiLimiterAlgorithmMap;
    private final String algorithmType = "TokenBucket";
    public ApiLimiterServiceImplement(){
        apiLimiterAlgorithmMap = new HashMap<>();
    }
    @Override
    public boolean isRequestAllowed(Long userId) {
        ApiLimiterAlgorithm apiLimiterAlgorithm = apiLimiterAlgorithmMap.getOrDefault(userId, null);
        if (apiLimiterAlgorithm!=null){
            return apiLimiterAlgorithm.isRequestAllowed();
        }
        apiLimiterAlgorithm = createAlgorithm(this.algorithmType);
        apiLimiterAlgorithmMap.put(userId,apiLimiterAlgorithm);
        return apiLimiterAlgorithm.isRequestAllowed();
    }
    private ApiLimiterAlgorithm createAlgorithm(String algorithmType){
        switch (algorithmType){
            case "LeakyBucket":
                return new LeakyBucket();
            default:
                return new TokenBucket();
        }
    }

}
