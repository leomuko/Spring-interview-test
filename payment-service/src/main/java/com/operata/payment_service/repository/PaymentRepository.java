package com.operata.payment_service.repository;

import com.operata.payment_service.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    //For checking user payment status
    Optional<Payment> findByReferenceId(String referenceId);

    // To prevent duplicate webhook processing.
    Optional<Payment> findByIdempotencyKey(String idempotencyKey);
}
