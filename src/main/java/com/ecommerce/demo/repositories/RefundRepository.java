package com.ecommerce.demo.repositories;

import com.ecommerce.demo.Entities.Refund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefundRepository extends JpaRepository<Refund,String> {
}
