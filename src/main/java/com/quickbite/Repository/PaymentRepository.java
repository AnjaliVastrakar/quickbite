
package com.quickbite.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quickbite.Entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByUserId(Long userId);

    List<Payment> findByOrderId(Long orderId);

    List<Payment> findByPaymentStatus(String paymentStatus);
}
