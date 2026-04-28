package com.operata.payment_service.service;

import com.operata.payment_service.dto.PaymentRequest;
import com.operata.payment_service.dto.PaymentResponse;
import com.operata.payment_service.dto.WebhookRequest;
import com.operata.payment_service.entity.Payment;
import com.operata.payment_service.enums.PaymentStatus;
import com.operata.payment_service.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public PaymentResponse initiatePayment(PaymentRequest request, String verifiedEmail) {
        Payment payment = new Payment();

        payment.setReferenceId(UUID.randomUUID().toString());

        payment.setUserEmail(verifiedEmail);
        payment.setAmount(request.getAmount());
        payment.setCurrency(request.getCurrency());

        payment.setStatus(PaymentStatus.INITIATED);

        paymentRepository.save(payment);

        return new PaymentResponse(payment.getReferenceId(), payment.getStatus().name());
    }

    @Transactional
    public String processWebhook(WebhookRequest webhookRequest) {

        //Check if Key Already Exists
        if(paymentRepository.findByIdempotencyKey(webhookRequest.getIdempotencyKey()).isPresent()){
            return "Webhook already processed (Idempotency check passed)";
        }

        // Find Original Payment
        Payment payment = paymentRepository.findByReferenceId(webhookRequest.getReferenceId())
                .orElseThrow(() -> new RuntimeException("Payment reference not found"));

        if (payment.getStatus() == PaymentStatus.SUCCESS || payment.getStatus() == PaymentStatus.FAILED) {
            return "Payment is already finalized. Cannot change status.";
        }

        payment.setStatus(PaymentStatus.valueOf(webhookRequest.getStatus().toUpperCase()));

        paymentRepository.save(payment);

        return "Payment status successfully updated to " + payment.getStatus();

    }
}
