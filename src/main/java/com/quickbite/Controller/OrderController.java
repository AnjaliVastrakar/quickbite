
package com.quickbite.Controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quickbite.DTO.OrderDTO;
import com.quickbite.Service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // CREATE ORDER
    @PostMapping
    public OrderDTO createOrder(@RequestBody OrderDTO orderDTO) {

        return orderService.createOrder(orderDTO);
    }

    // GET ORDERS BY USER ID AND STATUS
    @GetMapping("/user/{userId}/status/{status}")
    public List<OrderDTO> getOrdersByUserIdAndStatus(
            @PathVariable Long userId,
            @PathVariable String status) {

        return orderService.getOrdersByUserIdAndStatus(userId, status);
    }

    // GET ALL ORDERS
    @GetMapping
    public List<OrderDTO> getAllOrders() {

        return orderService.getAllOrders();
    }

    // GET ALL ORDERS BY USER ID
    @GetMapping("/user/{userId}")
    public List<OrderDTO> getOrdersByUserId(
            @PathVariable Long userId) {

        return orderService.getOrdersByUserId(userId);
    }

    // GET ALL ORDERS BY STATUS
    @GetMapping("/status/{status}")
    public List<OrderDTO> getOrdersByStatus(
            @PathVariable String status) {

        return orderService.getOrdersByStatus(status);
    }

    // GET ORDER BY ID
    @GetMapping("/{id}")
    public OrderDTO getOrderById(
            @PathVariable Long id) {

        return orderService.getOrderById(id);
    }

    // UPDATE ORDER STATUS
    @PutMapping("/{id}/status")
    public OrderDTO updateOrderStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        System.out.println("========== UPDATE ORDER STATUS ==========");
        System.out.println("CONTROLLER HIT");
        System.out.println("ORDER ID = " + id);
        System.out.println("STATUS = " + status);

        return orderService.updateOrderStatus(id, status);
    }

    // CANCEL ORDER
    @PutMapping("/{id}/cancel")
    public OrderDTO cancelOrder(
            @PathVariable Long id,
            @RequestParam Long userId) {

        System.out.println("========== CANCEL ORDER ==========");
        System.out.println("ORDER ID = " + id);
        System.out.println("USER ID = " + userId);

        return orderService.cancelOrder(id, userId);
    }

    // DELETE ORDER
    @DeleteMapping("/{id}")
    public String deleteOrder(
            @PathVariable Long id) {

        orderService.deleteOrder(id);

        return "Order deleted successfully";
    }

    // GET ORDERS WITH PAGINATION
    @GetMapping("/page")
    public Page<OrderDTO> getOrdersWithPagination(
            Pageable pageable) {

        return orderService.getOrdersWithPagination(pageable);
    }

    // UPDATE COMPLETE ORDER
    @PutMapping("/{id}")
    public OrderDTO updateOrder(
            @PathVariable Long id,
            @RequestBody OrderDTO orderDTO) {

        return orderService.updateOrder(id, orderDTO);
    }
}

