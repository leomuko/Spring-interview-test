package com.operata.payment_service.dto;

public class WebhookRequest {
    private String referenceId;
    private String status; // SUCCESS or FAILED
    private String idempotencyKey; // Unique ID from the provider for this specific event


    public String getReferenceId() { return referenceId; }
    public void setReferenceId(String referenceId) { this.referenceId = referenceId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getIdempotencyKey() { return idempotencyKey; }
    public void setIdempotencyKey(String idempotencyKey) { this.idempotencyKey = idempotencyKey; }
}
