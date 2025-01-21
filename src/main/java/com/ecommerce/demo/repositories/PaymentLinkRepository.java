package com.ecommerce.demo.repositories;

import com.ecommerce.demo.dtos.paymentlinkdto.RazorpayDtos.PaymentLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping
public interface PaymentLinkRepository extends JpaRepository<PaymentLink,String> {
}
