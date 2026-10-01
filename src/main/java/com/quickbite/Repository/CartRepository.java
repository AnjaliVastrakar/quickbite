
package com.quickbite.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quickbite.Entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findByUserId(Long userId);

    List<Cart> findByUserIdAndMenuItemId(Long userId, Long menuItemId);
}
