package com.operata.payment_service.dto;

import java.math.BigDecimal;

public class PaymentRequest {
    private BigDecimal amount;
    private String currency;

    // NOTE: In Phase 3, we will extract this securely from the JWT.
    // For now, we will pass it in the request so we can test Phase 2!
    private String userEmail;

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
}
