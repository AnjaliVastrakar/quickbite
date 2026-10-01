
package com.quickbite.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.quickbite.DTO.CartDTO;
import com.quickbite.Entity.Cart;
import com.quickbite.Entity.MenuItem;
import com.quickbite.Repository.CartRepository;
import com.quickbite.Repository.MenuItemRepository;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final MenuItemRepository menuItemRepository;

    public CartService(
            CartRepository cartRepository,
            MenuItemRepository menuItemRepository) {

        this.cartRepository = cartRepository;
        this.menuItemRepository = menuItemRepository;
    }

    // ADD ITEM TO CART
    public CartDTO addToCart(CartDTO dto) {

        // Check if this menu item already exists in user's cart
        List<Cart> existingItems =
                cartRepository.findByUserIdAndMenuItemId(
                        dto.getUserId(),
                        dto.getMenuItemId()
                );

        if (!existingItems.isEmpty()) {

            // If item already exists, increase quantity
            Cart existingCart = existingItems.get(0);

            existingCart.setQuantity(
                    existingCart.getQuantity() + dto.getQuantity()
            );

            Cart updatedCart = cartRepository.save(existingCart);

            return convertToDTO(updatedCart);
        }

        // If item does not exist, create new cart item
        Cart cart = new Cart();

        cart.setUserId(dto.getUserId());
        cart.setMenuItemId(dto.getMenuItemId());
        cart.setQuantity(dto.getQuantity());
        cart.setPrice(dto.getPrice());

        Cart savedCart = cartRepository.save(cart);

        return convertToDTO(savedCart);
    }

    // GET USER CART
    public List<CartDTO> getCartByUserId(Long userId) {

        return cartRepository.findByUserId(userId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // UPDATE CART QUANTITY
    public CartDTO updateQuantity(Long id, Integer quantity) {

        Cart cart = cartRepository.findById(id)
                .orElseThrow(() ->
                    new RuntimeException("Cart item not found")
                );

        cart.setQuantity(quantity);

        Cart updatedCart = cartRepository.save(cart);

        return convertToDTO(updatedCart);
    }

    // DELETE CART ITEM
    public void removeFromCart(Long id) {

        Cart cart = cartRepository.findById(id)
                .orElseThrow(() ->
                    new RuntimeException("Cart item not found")
                );

        cartRepository.delete(cart);
    }

    // CALCULATE CART TOTAL
    public BigDecimal getCartTotal(Long userId) {

        List<Cart> cartItems = cartRepository.findByUserId(userId);

        return cartItems.stream()
                .map(item ->
                    item.getPrice()
                        .multiply(
                            BigDecimal.valueOf(item.getQuantity())
                        )
                )
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // ENTITY → DTO
    private CartDTO convertToDTO(Cart cart) {

        String menuItemName = menuItemRepository
                .findById(cart.getMenuItemId())
                .map(MenuItem::getName)
                .orElse("Unknown Item");

        return new CartDTO(
                cart.getId(),
                cart.getUserId(),
                cart.getMenuItemId(),
                menuItemName,
                cart.getQuantity(),
                cart.getPrice()
        );
    }

    // CLEAR CART
    public void clearCart(Long userId) {

        List<Cart> cartItems =
                cartRepository.findByUserId(userId);

        cartRepository.deleteAll(cartItems);
    }
}

