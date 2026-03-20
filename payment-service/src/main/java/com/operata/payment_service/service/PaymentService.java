package com.operata.payment_service.service;

import com.operata.payment_service.dto.PaymentRequest;
import com.operata.payment_service.dto.PaymentResponse;
import com.operata.payment_service.entity.Payment;
import com.operata.payment_service.enums.PaymentStatus;
import com.operata.payment_service.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public PaymentResponse initiatePayment(PaymentRequest request) {
        Payment payment = new Payment();

        payment.setReferenceId(UUID.randomUUID().toString());

        payment.setUserEmail(request.getUserEmail());
        payment.setAmount(request.getAmount());
        payment.setCurrency(request.getCurrency());

        payment.setStatus(PaymentStatus.INITIATED);

        paymentRepository.save(payment);

        return new PaymentResponse(payment.getReferenceId(), payment.getStatus().name());
    }
}
