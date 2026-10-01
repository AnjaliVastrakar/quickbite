
package com.quickbite.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.quickbite.DTO.OrderDTO;
import com.quickbite.Entity.Order;
import com.quickbite.Repository.OrderRepository;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartService cartService;

    public OrderService(
            OrderRepository orderRepository,
            CartService cartService) {

        this.orderRepository = orderRepository;
        this.cartService = cartService;
    }

    private OrderDTO convertToDTO(Order order) {

        return new OrderDTO(
                order.getId(),
                order.getUserId(),
                order.getTotalAmount(),
                order.getStatus()
        );
    }

    private Order convertToEntity(OrderDTO dto) {

        Order order = new Order();

        order.setUserId(dto.getUserId());
        order.setTotalAmount(dto.getTotalAmount());
        order.setStatus(dto.getStatus());

        return order;
    }

    // CREATE ORDER FROM CART
    public OrderDTO createOrder(OrderDTO orderDTO) {

        Long userId = orderDTO.getUserId();

        // Calculate total from user's cart
        BigDecimal cartTotal = cartService.getCartTotal(userId);

        // Check if cart is empty
        if (cartTotal.compareTo(BigDecimal.ZERO) <= 0) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Cart is empty"
            );
        }

        // Create order
        Order order = new Order();

        order.setUserId(userId);
        order.setTotalAmount(cartTotal);
        order.setStatus("PLACED");

        Order savedOrder = orderRepository.save(order);

        // Clear cart after successful order creation
        cartService.clearCart(userId);

        return convertToDTO(savedOrder);
    }

    // GET ALL ORDERS
    public List<OrderDTO> getAllOrders() {

        return orderRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // GET ORDER BY ID
    public OrderDTO getOrderById(Long id) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() ->
                    new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Order not found with id: " + id
                    )
                );

        return convertToDTO(order);
    }

    // UPDATE COMPLETE ORDER
    public OrderDTO updateOrder(Long id, OrderDTO orderDTO) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() ->
                    new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Order not found with id: " + id
                    )
                );

        order.setUserId(orderDTO.getUserId());
        order.setTotalAmount(orderDTO.getTotalAmount());

        validateStatus(orderDTO.getStatus());

        order.setStatus(orderDTO.getStatus());

        Order updatedOrder = orderRepository.save(order);

        return convertToDTO(updatedOrder);
    }

    // CANCEL ORDER
    public OrderDTO cancelOrder(Long id, Long userId) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() ->
                    new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Order not found with id: " + id
                    )
                );

        // Make sure the order belongs to this user
        if (!order.getUserId().equals(userId)) {

            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You are not allowed to cancel this order"
            );
        }

        // Check current order status
        if ("OUT_FOR_DELIVERY".equals(order.getStatus())
                || "DELIVERED".equals(order.getStatus())
                || "CANCELLED".equals(order.getStatus())) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Order cannot be cancelled at this stage"
            );
        }

        order.setStatus("CANCELLED");

        Order updatedOrder = orderRepository.save(order);

        return convertToDTO(updatedOrder);
    }

    // GET ORDERS BY USER ID
    public List<OrderDTO> getOrdersByUserId(Long userId) {

        return orderRepository.findByUserId(userId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // GET ORDERS BY STATUS
    public List<OrderDTO> getOrdersByStatus(String status) {

        return orderRepository.findByStatus(status)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // GET ORDERS BY USER ID AND STATUS
    public List<OrderDTO> getOrdersByUserIdAndStatus(
            Long userId,
            String status) {

        return orderRepository.findByUserIdAndStatus(userId, status)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // UPDATE ORDER STATUS
    public OrderDTO updateOrderStatus(Long id, String status) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() ->
                    new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Order not found with id: " + id
                    )
                );

        validateStatus(status);

        order.setStatus(status);

        Order updatedOrder = orderRepository.save(order);

        return convertToDTO(updatedOrder);
    }

    // VALIDATE ORDER STATUS
    private void validateStatus(String status) {

        if (!"PLACED".equals(status)
                && !"PREPARING".equals(status)
                && !"OUT_FOR_DELIVERY".equals(status)
                && !"DELIVERED".equals(status)
                && !"CANCELLED".equals(status)) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid order status: " + status
            );
        }
    }

    // DELETE ORDER
    public void deleteOrder(Long id) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() ->
                    new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Order not found with id: " + id
                    )
                );

        orderRepository.delete(order);
    }

    // PAGINATION
    public Page<OrderDTO> getOrdersWithPagination(
            Pageable pageable) {

        return orderRepository.findAll(pageable)
                .map(this::convertToDTO);
    }
}
