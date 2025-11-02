package com.deniz.payment_service.repository;

import com.deniz.payment_service.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByStripeSessionId(String stripeSessionId);
}
