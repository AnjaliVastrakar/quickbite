
package com.quickbite.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quickbite.Entity.MenuItem;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    List<MenuItem> findByRestaurantId(Long restaurantId);

    List<MenuItem> findByCategory(String category);

    List<MenuItem> findByAvailable(boolean available);

    // Search by menu item name
    List<MenuItem> findByNameContainingIgnoreCase(String name);

    // Search by name OR category
    List<MenuItem> findByNameContainingIgnoreCaseOrCategoryContainingIgnoreCase(
            String name,
            String category
    );
}
