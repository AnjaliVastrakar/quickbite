
package com.quickbite.Controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.quickbite.DTO.CartDTO;
import com.quickbite.Service.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // ADD ITEM TO CART
    @PostMapping
    public CartDTO addToCart(@RequestBody CartDTO dto) {
        return cartService.addToCart(dto);
    }

    // GET USER CART
    @GetMapping("/user/{userId}")
    public List<CartDTO> getCartByUserId(
            @PathVariable Long userId) {

        return cartService.getCartByUserId(userId);
    }

    // UPDATE CART QUANTITY
    @PutMapping("/{id}")
    public CartDTO updateQuantity(
            @PathVariable Long id,
            @RequestParam Integer quantity) {

        return cartService.updateQuantity(id, quantity);
    }

    // REMOVE ITEM FROM CART
    @DeleteMapping("/{id}")
    public String removeFromCart(@PathVariable Long id) {

        cartService.removeFromCart(id);

        return "Item removed from cart successfully";
    }

    // GET CART TOTAL
    @GetMapping("/user/{userId}/total")
    public BigDecimal getCartTotal(
            @PathVariable Long userId) {

        return cartService.getCartTotal(userId);
    }
}
