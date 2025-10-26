package com.deniz.payment_service.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;
    private Long quantity;
    private Long amount;  // in normal currency, not cents
    private String currency;

    private String stripeSessionId;

    @Column(columnDefinition = "TEXT")
    private String stripeSessionUrl;

    private String status; // e.g. "PENDING", "PAID", "CANCELLED"

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist()
    {
        createdAt = LocalDateTime.now();
        if (status == null)
        {
            status = "PENDING";
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public Long getQuantity() { return quantity; }
    public void setQuantity(Long quantity) { this.quantity = quantity; }
    public Long getAmount() { return amount; }
    public void setAmount(Long amount) { this.amount = amount; }
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    public String getStripeSessionId() { return stripeSessionId; }
    public void setStripeSessionId(String stripeSessionId) { this.stripeSessionId = stripeSessionId; }
    public String getStripeSessionUrl() { return stripeSessionUrl; }
    public void setStripeSessionUrl(String stripeSessionUrl) { this.stripeSessionUrl = stripeSessionUrl; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

}
