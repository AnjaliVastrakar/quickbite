
package com.quickbite.Entity;

import java.math.BigDecimal;

import jakarta.persistence.*;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "payment_seq"
    )
    @SequenceGenerator(
        name = "payment_seq",
        sequenceName = "PAYMENT_SEQ",
        allocationSize = 1
    )
    private Long id;

    private Long orderId;

    private Long userId;

    @Column(precision = 10, scale = 2)
    private BigDecimal amount;

    private String paymentMethod;

    private String paymentStatus;

    public Payment() {
    }

    public Payment(
            Long id,
            Long orderId,
            Long userId,
            BigDecimal amount,
            String paymentMethod,
            String paymentStatus) {

        this.id = id;
        this.orderId = orderId;
        this.userId = userId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
