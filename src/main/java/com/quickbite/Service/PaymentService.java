
package com.quickbite.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import com.quickbite.DTO.PaymentDTO;
import com.quickbite.Entity.Order;
import com.quickbite.Entity.Payment;
import com.quickbite.Repository.OrderRepository;
import com.quickbite.Repository.PaymentRepository;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public PaymentService(
            PaymentRepository paymentRepository,
            OrderRepository orderRepository) {

        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

    // CREATE PAYMENT
    @Transactional
    public PaymentDTO createPayment(PaymentDTO dto) {

        if (dto.getOrderId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "orderId is required");
        }

        validateMethod(dto.getPaymentMethod());

        // Find order
        Order order = orderRepository.findById(dto.getOrderId())
                .orElseThrow(() ->
                    new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Order not found with id: " + dto.getOrderId()
                    )
                );

        if ("CANCELLED".equals(order.getStatus())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Cannot pay for a cancelled order");
        }

        // Idempotency: return existing SUCCESS payment instead of double-charging
        List<PaymentDTO> existing = getPaymentsByOrderId(order.getId())
                .stream()
                .filter(p -> "SUCCESS".equals(p.getPaymentStatus()))
                .toList();
        if (!existing.isEmpty()) {
            return existing.get(0);
        }

        // Create payment
        Payment payment = new Payment();

        payment.setOrderId(order.getId());
        payment.setUserId(order.getUserId());

        // Take amount directly from order
        payment.setAmount(order.getTotalAmount());

        payment.setPaymentMethod(dto.getPaymentMethod());

        // Simulate successful payment
        payment.setPaymentStatus("SUCCESS");

        Payment savedPayment = paymentRepository.save(payment);

        return convertToDTO(savedPayment);
    }

    // GET ALL PAYMENTS
    public List<PaymentDTO> getAllPayments() {

        return paymentRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // GET PAYMENT BY ID
    public PaymentDTO getPaymentById(Long id) {

        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() ->
                    new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Payment not found with id: " + id
                    )
                );

        return convertToDTO(payment);
    }

    // GET PAYMENTS BY USER
    public List<PaymentDTO> getPaymentsByUserId(Long userId) {

        return paymentRepository.findByUserId(userId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // GET PAYMENT BY ORDER
    public List<PaymentDTO> getPaymentsByOrderId(Long orderId) {

        return paymentRepository.findByOrderId(orderId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // GET PAYMENTS BY STATUS
    public List<PaymentDTO> getPaymentsByStatus(String status) {

        return paymentRepository.findByPaymentStatus(status)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // ENTITY → DTO
    private PaymentDTO convertToDTO(Payment payment) {

        return new PaymentDTO(
                payment.getId(),
                payment.getOrderId(),
                payment.getUserId(),
                payment.getAmount(),
                payment.getPaymentMethod(),
                payment.getPaymentStatus()
        );
    }

    private void validateMethod(String method) {
        if (!"UPI".equals(method) && !"CARD".equals(method) && !"CASH".equals(method)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid payment method: " + method + ". Use UPI, CARD or CASH");
        }
    }
}
