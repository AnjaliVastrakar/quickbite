
package com.quickbite.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.quickbite.DTO.PaymentDTO;
import com.quickbite.Service.PaymentService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    // CREATE PAYMENT
    @PostMapping
    public PaymentDTO createPayment(
            @Valid @RequestBody PaymentDTO dto) {

        return paymentService.createPayment(dto);
    }

    // GET ALL PAYMENTS
    @GetMapping
    public List<PaymentDTO> getAllPayments() {

        return paymentService.getAllPayments();
    }

    // GET PAYMENT BY ID
    @GetMapping("/{id}")
    public PaymentDTO getPaymentById(
            @PathVariable Long id) {

        return paymentService.getPaymentById(id);
    }

    // GET PAYMENTS BY USER
    @GetMapping("/user/{userId}")
    public List<PaymentDTO> getPaymentsByUserId(
            @PathVariable Long userId) {

        return paymentService.getPaymentsByUserId(userId);
    }

    // GET PAYMENTS BY ORDER
    @GetMapping("/order/{orderId}")
    public List<PaymentDTO> getPaymentsByOrderId(
            @PathVariable Long orderId) {

        return paymentService.getPaymentsByOrderId(orderId);
    }

    // GET PAYMENTS BY STATUS
    @GetMapping("/status/{status}")
    public List<PaymentDTO> getPaymentsByStatus(
            @PathVariable String status) {

        return paymentService.getPaymentsByStatus(status);
    }
}

