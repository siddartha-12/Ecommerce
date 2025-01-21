package com.ecommerce.demo.configurations;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RazorpayClientConfiguration {
    @Bean
    public RazorpayClient getRazorPayclient() throws RazorpayException {
        return new RazorpayClient("rzp_test_968nYSYNBYXgVC","kRX0seSkvVDBklgzehHFZxOw");
    }
}
