package com.operata.payment_service.controller;

import com.operata.payment_service.dto.PaymentRequest;
import com.operata.payment_service.dto.PaymentResponse;
import com.operata.payment_service.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/initiate")
    public ResponseEntity<PaymentResponse> initiatePayment(@RequestBody PaymentRequest request, HttpServletRequest httpServletRequest) {

        // Grab the verified email extracted by our AuthFilter
        String verifiedEmail = (String) httpServletRequest.getAttribute("userEmail");

        PaymentResponse response = paymentService.initiatePayment(request, verifiedEmail);
        return ResponseEntity.ok(response);
    }
}
