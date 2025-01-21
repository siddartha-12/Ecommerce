package com.ecommerce.demo.repositories;

import com.ecommerce.demo.Entities.CustomerOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {
public Page<CustomerOrder> findAllByUserId(Long id, Pageable pageable);
}


