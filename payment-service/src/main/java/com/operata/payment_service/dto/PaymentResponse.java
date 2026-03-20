package com.operata.payment_service.dto;

public class PaymentResponse {
    private String referenceId;
    private String status;

    public PaymentResponse(String referenceId, String status) {
        this.referenceId = referenceId;
        this.status = status;
    }

    // Getters and Setters
    public String getReferenceId() { return referenceId; }
    public void setReferenceId(String referenceId) { this.referenceId = referenceId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
