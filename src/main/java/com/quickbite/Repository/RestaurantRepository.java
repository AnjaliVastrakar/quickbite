
package com.quickbite.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quickbite.Entity.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    List<Restaurant> findByCuisine(String cuisine);

    List<Restaurant> findByLocation(String location);

    // Search by restaurant name
    List<Restaurant> findByNameContainingIgnoreCase(String name);

    // Search by name OR location OR cuisine
    List<Restaurant> findByNameContainingIgnoreCaseOrLocationContainingIgnoreCaseOrCuisineContainingIgnoreCase(
            String name,
            String location,
            String cuisine
    );
}

